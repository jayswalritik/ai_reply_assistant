package com.aireplyassistant.domain.model

/**
 * MessageContext - Data model for extracted chat context.
 */
data class MessageContext(
    val latestMessage: String,
    val sender: String? = null,
    val appName: String? = null,
    val conversationHistory: List<String> = emptyList()
)
