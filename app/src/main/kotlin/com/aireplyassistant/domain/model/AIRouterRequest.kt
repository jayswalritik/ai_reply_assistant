package com.aireplyassistant.domain.model

/**
 * AIRouterRequest - Request to AI Router for reply generation.
 */
data class AIRouterRequest(
    val messageContext: MessageContext,
    val replyStyle: String = "CASUAL",  // FUNNY, CASUAL, FRIENDLY, PROFESSIONAL, ROMANTIC, SAVAGE
    val mode: AIMode = AIMode.ADAPTIVE
)

/**
 * AIReply - Single AI-generated reply.
 */
data class AIReply(
    val text: String,
    val style: String,
    val confidence: Float = 1.0f
)
