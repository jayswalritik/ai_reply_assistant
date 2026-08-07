package com.aireplyassistant.presentation.keyboard

import android.inputmethodservice.InputMethodService
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
 import android.view.ContextThemeWrapper
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.aireplyassistant.R
import com.aireplyassistant.domain.model.OverlayMode
import com.aireplyassistant.domain.repository.AccessibilityRepository
import com.aireplyassistant.domain.usecase.GenerateRepliesUseCase
import com.aireplyassistant.presentation.accessibility.FloatingScanIndicator
import com.aireplyassistant.presentation.ui.theme.AIReplyAssistantTheme

@AndroidEntryPoint
class AIKeyboardService : InputMethodService(), LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner, HasDefaultViewModelProviderFactory {

    @Inject lateinit var generateRepliesUseCase: GenerateRepliesUseCase
    @Inject lateinit var accessibilityRepository: AccessibilityRepository

    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }
    private val store by lazy { ViewModelStore() }
    private val savedStateRegistryController by lazy { SavedStateRegistryController.create(this) }

    override val lifecycle: Lifecycle get() = lifecycleRegistry
    override val viewModelStore: ViewModelStore get() = store
    override val savedStateRegistry: SavedStateRegistry get() = savedStateRegistryController.savedStateRegistry

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(KeyboardViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return KeyboardViewModel(generateRepliesUseCase, accessibilityRepository, applicationContext) as T
                }
                return super.create(modelClass)
            }
        }

    override val defaultViewModelCreationExtras: CreationExtras get() = CreationExtras.Empty

    private var viewModel: KeyboardViewModel? = null
    private var currentInputConnection: InputConnection? = null
    private var floatingIndicator: FloatingScanIndicator? = null
    private var pendingReply: String? = null

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performAttach()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        
        viewModel = ViewModelProvider(this)[KeyboardViewModel::class.java]
        observeVisibilityRequests()
        
        floatingIndicator = FloatingScanIndicator(this) {
            accessibilityRepository.showOverlay(OverlayMode.SINGLE_SELECT)
            floatingIndicator?.hide()
        }
    }

    private fun observeVisibilityRequests() {
        val vm = viewModel ?: return
        lifecycle.coroutineScope.launch {
            vm.keyboardVisibilityRequests.collect { visible ->
                if (visible) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) requestShowSelf(0)
                } else requestHideSelf(0)
            }
        }
        lifecycle.coroutineScope.launch {
            accessibilityRepository.floatingIndicatorRequests.collect { visible ->
                if (visible) floatingIndicator?.show() else floatingIndicator?.hide()
            }
        }
        lifecycle.coroutineScope.launch {
            vm.replyToCommit.collect { reply ->
                if (currentInputConnection != null) {
                    inputCharacter(reply)
                } else {
                    pendingReply = reply
                }
            }
        }
    }

    private fun checkOverlayPermission(): Boolean {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, android.net.Uri.parse("package:$packageName"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return false
        }
        return true
    }

    override fun onInitializeInterface() {
        super.onInitializeInterface()
        window?.window?.decorView?.let { decorView ->
            decorView.setViewTreeLifecycleOwner(this)
            decorView.setViewTreeViewModelStoreOwner(this)
            decorView.setViewTreeSavedStateRegistryOwner(this)
        }
    }

    override fun onCreateInputView(): View {
        val root = FrameLayout(ContextThemeWrapper(this, R.style.Theme_AIReplyAssistant))
        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val composeView = ComposeView(root.context).apply {
            layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            setViewTreeLifecycleOwner(this@AIKeyboardService)
            setViewTreeViewModelStoreOwner(this@AIKeyboardService)
            setViewTreeSavedStateRegistryOwner(this@AIKeyboardService)
            setContent {
                viewModel?.let { vm ->
                    AIReplyAssistantTheme {
                        KeyboardScreen(
                            viewModel = vm,
                            onCharacterInput = { vm.onCharacterInput(it); inputCharacter(it) },
                            onBackspace = { vm.onBackspace(); inputBackspace() },
                            onSpace = { vm.onSpace(); inputSpace() },
                            onEnter = { inputEnter() },
                            onAIButtonPressed = { handleAIButtonPressed() },
                            onReplySelected = { vm.insertReply(it); insertReply(it) }
                        )
                    }
                }
            }
        }
        root.addView(composeView)
        return root
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        currentInputConnection = getCurrentInputConnection()
        if (!restarting) viewModel?.reset()
        viewModel?.setKeyboardMode(KeyboardMode.ALPHA)
        
        // Commit pending reply if we just came back from ChatGPT
        pendingReply?.let {
            inputCharacter(it)
            pendingReply = null
        }
    }

    override fun onWindowShown() {
        super.onWindowShown()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onWindowHidden() {
        super.onWindowHidden()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onFinishInput() { super.onFinishInput(); currentInputConnection = null }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        store.clear()
        floatingIndicator?.hide()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_DEL -> { inputBackspace(); true }
            KeyEvent.KEYCODE_ENTER -> { inputEnter(); true }
            KeyEvent.KEYCODE_SPACE -> { inputSpace(); true }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    private fun inputCharacter(text: String) { currentInputConnection?.commitText(text, 1) }
    private fun inputBackspace() { currentInputConnection?.deleteSurroundingText(1, 0) }
    private fun inputSpace() { currentInputConnection?.commitText(" ", 1) }
    private fun inputEnter() { currentInputConnection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER)) }

    private fun handleAIButtonPressed() {
        if (checkOverlayPermission()) viewModel?.onAIButtonPressed()
    }

    private fun insertReply(reply: String) { currentInputConnection?.commitText(reply, 1) }
}
