package com.aireplyassistant.data.repository

import com.aireplyassistant.domain.model.AIProvider
import com.aireplyassistant.domain.model.MessageBubble
import com.aireplyassistant.domain.model.OverlayMode
import com.aireplyassistant.domain.repository.AccessibilityRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessibilityRepositoryImpl @Inject constructor() : AccessibilityRepository {

    private val _overlayVisibilityRequests = MutableSharedFlow<Pair<Boolean, OverlayMode>>(extraBufferCapacity = 1)
    override val overlayVisibilityRequests: SharedFlow<Pair<Boolean, OverlayMode>> = _overlayVisibilityRequests.asSharedFlow()

    private val _selectionResults = MutableSharedFlow<Pair<List<MessageBubble>, OverlayMode>>(extraBufferCapacity = 1)
    override val selectionResults: SharedFlow<Pair<List<MessageBubble>, OverlayMode>> = _selectionResults.asSharedFlow()

    private val _keyboardVisibilityRequests = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    override val keyboardVisibilityRequests: SharedFlow<Boolean> = _keyboardVisibilityRequests.asSharedFlow()

    private val _activeProvider = MutableStateFlow<AIProvider?>(null)
    override val activeProvider: StateFlow<AIProvider?> = _activeProvider.asStateFlow()

    private val _floatingIndicatorRequests = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    override val floatingIndicatorRequests: SharedFlow<Boolean> = _floatingIndicatorRequests.asSharedFlow()

    private val _targetPackage = MutableStateFlow<String?>(null)
    override val targetPackage: StateFlow<String?> = _targetPackage.asStateFlow()

    private val _ocrCache = MutableStateFlow<List<MessageBubble>>(emptyList())
    override val ocrCache: StateFlow<List<MessageBubble>> = _ocrCache.asStateFlow()

    private val _saveChatOverlayRequests = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    override val saveChatOverlayRequests: SharedFlow<Boolean> = _saveChatOverlayRequests.asSharedFlow()

    private var scanBubblesProvider: (suspend (Boolean, Boolean) -> List<MessageBubble>)? = null
    private var captureUrlProvider: (suspend () -> String?)? = null

    override fun showOverlay(mode: OverlayMode) {
        _overlayVisibilityRequests.tryEmit(true to mode)
    }

    override suspend fun scanVisibleBubbles(isGeneric: Boolean, useVisualOcr: Boolean): List<MessageBubble> {
        return scanBubblesProvider?.invoke(isGeneric, useVisualOcr) ?: emptyList()
    }

    override suspend fun captureBrowserUrl(): String? {
        return captureUrlProvider?.invoke()
    }

    fun setScanBubblesProvider(provider: suspend (Boolean, Boolean) -> List<MessageBubble>) {
        this.scanBubblesProvider = provider
    }

    fun setCaptureUrlProvider(provider: suspend () -> String?) {
        this.captureUrlProvider = provider
    }

    override fun confirmSelection(selectedBubbles: List<MessageBubble>, mode: OverlayMode) {
        _selectionResults.tryEmit(selectedBubbles to mode)
        _overlayVisibilityRequests.tryEmit(false to mode)
    }

    override fun cancelSelection(mode: OverlayMode) {
        // Emit empty result so ViewModel can properly reset stuck state (not just close overlay)
        _selectionResults.tryEmit(emptyList<MessageBubble>() to mode)
        _overlayVisibilityRequests.tryEmit(false to mode)
    }

    override fun setKeyboardVisibility(visible: Boolean) {
        _keyboardVisibilityRequests.tryEmit(visible)
    }

    override fun setActiveProvider(provider: AIProvider?) {
        _activeProvider.value = provider
    }

    override fun setOcrCache(bubbles: List<MessageBubble>) {
        _ocrCache.value = bubbles
    }

    override fun setTargetPackage(packageName: String?) {
        _targetPackage.value = packageName
    }

    override fun setFloatingIndicatorVisibility(visible: Boolean) {
        _floatingIndicatorRequests.tryEmit(visible)
    }

    override fun setSaveChatOverlayVisibility(visible: Boolean) {
        _saveChatOverlayRequests.tryEmit(visible)
    }
}
