package com.aireplyassistant.domain.repository

import com.aireplyassistant.domain.model.AIProvider
import com.aireplyassistant.domain.model.MessageBubble
import com.aireplyassistant.domain.model.OverlayMode
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * AccessibilityRepository - Shared communication layer for accessibility events and overlay selection.
 */
interface AccessibilityRepository {
    
    /**
     * Request to show the selection overlay in a specific mode.
     */
    fun showOverlay(mode: OverlayMode = OverlayMode.MULTI_SELECT)

    /**
     * Flow of overlay visibility requests.
     */
    val overlayVisibilityRequests: SharedFlow<Pair<Boolean, OverlayMode>>

    /**
     * Request the Accessibility Service to scan all visible bubbles.
     * if useVisualOcr is true, it will use Google ML Kit OCR.
     */
    suspend fun scanVisibleBubbles(isGeneric: Boolean = false, useVisualOcr: Boolean = false): List<MessageBubble>

    /**
     * Result of the selection process.
     */
    fun confirmSelection(selectedBubbles: List<MessageBubble>, mode: OverlayMode)

    /**
     * Flow of selected messages.
     */
    val selectionResults: SharedFlow<Pair<List<MessageBubble>, OverlayMode>>

    /**
     * Cancel the selection process.
     */
    fun cancelSelection(mode: OverlayMode)

    /**
     * Request the keyboard to hide or show.
     */
    fun setKeyboardVisibility(visible: Boolean)

    /**
     * Flow of keyboard visibility requests.
     */
    val keyboardVisibilityRequests: SharedFlow<Boolean>

    /**
     * Set the active AI Provider.
     */
    fun setActiveProvider(provider: AIProvider?)
    
    /**
     * The currently selected AI provider.
     */
    val activeProvider: StateFlow<AIProvider?>

    /**
     * Show/Hide floating ChatGPT scan indicator.
     */
    fun setFloatingIndicatorVisibility(visible: Boolean)
    
    /**
     * Flow for floating indicator visibility.
     */
    val floatingIndicatorRequests: SharedFlow<Boolean>

    /**
     * Set/Get current target app package.
     */
    fun setTargetPackage(packageName: String?)
    val targetPackage: StateFlow<String?>

    /**
     * Cache for OCR results to avoid re-scanning.
     */
    fun setOcrCache(bubbles: List<MessageBubble>)
    val ocrCache: StateFlow<List<MessageBubble>>

    /**
     * Show/Hide "Save This Chat" overlay for first-time setup.
     */
    fun setSaveChatOverlayVisibility(visible: Boolean)
    val saveChatOverlayRequests: SharedFlow<Boolean>

    /**
     * Request the Accessibility Service to capture the current browser URL.
     */
    suspend fun captureBrowserUrl(): String?
}
