package com.aireplyassistant.domain.model

import android.graphics.Rect

/**
 * MessageBubble - Represents a single chat message bubble extracted from the screen.
 */
data class MessageBubble(
    val text: String,
    val isSentByUser: Boolean,
    val sender: String? = null,
    val bounds: Rect = Rect()
)
