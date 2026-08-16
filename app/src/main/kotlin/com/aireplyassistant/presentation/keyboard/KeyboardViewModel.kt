package com.aireplyassistant.presentation.keyboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aireplyassistant.domain.model.*
import com.aireplyassistant.domain.repository.AccessibilityRepository
import com.aireplyassistant.domain.usecase.GenerateRepliesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@HiltViewModel
class KeyboardViewModel @Inject constructor(
    private val generateRepliesUseCase: GenerateRepliesUseCase,
    private val accessibilityRepository: AccessibilityRepository,
    private val chatGptRepository: com.aireplyassistant.domain.repository.ChatGptConversationRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _currentText = mutableStateOf("")
    val currentText: State<String> = _currentText

    private val _showReplySuggestions = MutableStateFlow(false)
    val showReplySuggestions: StateFlow<Boolean> = _showReplySuggestions.asStateFlow()

    private val _isLoadingReplies = MutableStateFlow(false)
    val isLoadingReplies: StateFlow<Boolean> = _isLoadingReplies.asStateFlow()

    private val _replySuggestions = MutableStateFlow<List<String>>(emptyList())
    val replySuggestions: StateFlow<List<String>> = _replySuggestions.asStateFlow()

    private val _recentEmojis = MutableStateFlow<List<String>>(emptyList())
    val recentEmojis: StateFlow<List<String>> = _recentEmojis.asStateFlow()

    private val _isShiftEnabled = MutableStateFlow(false)
    val isShiftEnabled: StateFlow<Boolean> = _isShiftEnabled.asStateFlow()

    private val _keyboardMode = MutableStateFlow(KeyboardMode.ALPHA)
    val keyboardMode: StateFlow<KeyboardMode> = _keyboardMode.asStateFlow()

    private val _selectedStyle = MutableStateFlow(ReplyStyle.CASUAL)
    val selectedStyle: StateFlow<ReplyStyle> = _selectedStyle.asStateFlow()

    private val _showProviderMenu = MutableStateFlow(false)
    val showProviderMenu: StateFlow<Boolean> = _showProviderMenu.asStateFlow()

    val keyboardVisibilityRequests = accessibilityRepository.keyboardVisibilityRequests
    val activeProvider = accessibilityRepository.activeProvider

    init {
        viewModelScope.launch {
            accessibilityRepository.selectionResults.collect { (bubbles, mode) ->
                handleSelection(bubbles, mode)
            }
        }
    }

    private var returnPackage: String? = null

    fun onAIButtonPressed() {
        // Capture the app we are currently in
        returnPackage = accessibilityRepository.targetPackage.value
        _showProviderMenu.value = true
    }

    fun onProviderSelected(provider: AIProvider) {
        _showProviderMenu.value = false
        accessibilityRepository.setActiveProvider(provider)
        accessibilityRepository.setKeyboardVisibility(false)
        accessibilityRepository.showOverlay(OverlayMode.MULTI_SELECT)
    }

    private fun handleSelection(bubbles: List<MessageBubble>, mode: OverlayMode) {
        if (bubbles.isEmpty()) {
            // Cancelled, or nothing found - fully reset so the NEXT attempt starts clean
            _isLoadingReplies.value = false
            accessibilityRepository.setFloatingIndicatorVisibility(false)
            accessibilityRepository.setActiveProvider(null)
            return
        }

        // Mode tells us definitively what this selection is for - no more guessing
        if (mode == OverlayMode.SINGLE_SELECT) {
            insertReply(bubbles.first().text)
            accessibilityRepository.setFloatingIndicatorVisibility(false)
            return
        }

        val provider = activeProvider.value ?: AIProvider.LOCAL
        if (provider == AIProvider.CHATGPT) {
            handleChatGptFlow(bubbles)
        } else {
            generateReplies(bubbles, provider)
        }
    }

    private fun generateReplies(bubbles: List<MessageBubble>, provider: AIProvider) {
        _isLoadingReplies.value = true
        _showReplySuggestions.value = true
        
        viewModelScope.launch {
            val transcript = buildTranscript(bubbles)
            val contextObj = MessageContext(
                latestMessage = bubbles.last().text,
                conversationHistory = if (bubbles.size > 1) bubbles.dropLast(1).map { it.text } else emptyList()
            )
            
            val response = generateRepliesUseCase.execute(
                messageContext = contextObj,
                style = _selectedStyle.value.name,
                mode = AIMode.ADAPTIVE,
                provider = provider
            )

            _replySuggestions.value = when (response) {
                is AIRouterResponse.Success -> response.replies
                else -> listOf("Error generating replies")
            }
            _isLoadingReplies.value = false
            accessibilityRepository.setKeyboardVisibility(true)
        }
    }

    private fun handleChatGptFlow(bubbles: List<MessageBubble>) {
        _isLoadingReplies.value = true
        val transcript = buildTranscript(bubbles)
        val prompt = "Here is our conversation:\n\n$transcript\n\nWrite 5 distinct natural replies I could send next. One per line."

        // 1. Copy to clipboard (Safety backup)
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("AI Prompt", prompt))

        // 2. Launch with ACTION_SEND to pre-fill prompt if supported
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, prompt)
            setPackage("com.openai.chatgpt") // Targeted launch
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            Toast.makeText(context, "Prompt copied! Paste it in ChatGPT", Toast.LENGTH_LONG).show()
            context.startActivity(sendIntent)
        } catch (e: Exception) {
            // Fallback: Just open app if ACTION_SEND package not found
            val launchIntent = context.packageManager.getLaunchIntentForPackage("com.openai.chatgpt")
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(launchIntent)
            } else {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://chatgpt.com"))
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(browserIntent)
            }
        }

        accessibilityRepository.setFloatingIndicatorVisibility(true)
    }

    private fun buildTranscript(bubbles: List<MessageBubble>): String {
        return bubbles.joinToString("\n") { 
            val sender = if (it.isSentByUser) "Me" else "Friend"
            "$sender: ${it.text}"
        }
    }

    fun onCharacterInput(text: String) {
        val input = if (_isShiftEnabled.value && text.length == 1 && text[0].isLetter()) text.uppercase() else text
        _currentText.value += input
    }

    fun addEmojiToRecents(emoji: String) {
        val current = _recentEmojis.value.toMutableList()
        current.remove(emoji)
        current.add(0, emoji)
        if (current.size > 32) current.removeAt(current.size - 1)
        _recentEmojis.value = current
    }

    fun toggleShift() { _isShiftEnabled.value = !_isShiftEnabled.value }
    fun setKeyboardMode(mode: KeyboardMode) { _keyboardMode.value = mode }
    fun onBackspace() { if (_currentText.value.isNotEmpty()) _currentText.value = _currentText.value.dropLast(1) }
    fun onSpace() { _currentText.value += " " }
    fun dismissProviderMenu() { _showProviderMenu.value = false }

    fun resetChatGPT() {
        viewModelScope.launch {
            chatGptRepository.clearUrl()
            Toast.makeText(context, "Saved chat reset!", Toast.LENGTH_SHORT).show()
        }
    }

    private val _replyToCommit = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val replyToCommit: SharedFlow<String> = _replyToCommit.asSharedFlow()

    fun insertReply(reply: String) {
        Log.d("KeyboardVM", "Emitting reply to commit: ${reply.take(20)}")
        _currentText.value = reply
        _showReplySuggestions.value = false
        _isLoadingReplies.value = false
        accessibilityRepository.setFloatingIndicatorVisibility(false)
        
        // Signal the keyboard service to commit this text
        _replyToCommit.tryEmit(reply)
        
        // Return to original app (WhatsApp/Messenger)
        returnPackage?.let { pkg ->
            Log.d("KeyboardVM", "Returning to: $pkg")
            val intent = context.packageManager.getLaunchIntentForPackage(pkg)
            intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    fun reset() {
        _currentText.value = ""
        _showReplySuggestions.value = false
        _replySuggestions.value = emptyList()
    }
}

enum class ReplyStyle { FUNNY, CASUAL, FRIENDLY, PROFESSIONAL, ROMANTIC, SAVAGE }
enum class KeyboardMode { ALPHA, NUMERIC, SYMBOLS, EMOJI, GIF }
