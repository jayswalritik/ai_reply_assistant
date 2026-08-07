package com.aireplyassistant.domain.model

/**
 * AccessibilityResult - Sealed class representing the result of context extraction.
 */
sealed class AccessibilityResult {
    data class Success(val bubbles: List<MessageBubble>) : AccessibilityResult()
    data class Error(val exception: Exception) : AccessibilityResult()
    object NoMessageFound : AccessibilityResult()
}
