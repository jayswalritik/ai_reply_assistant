package com.aireplyassistant.domain.repository

import com.aireplyassistant.domain.model.AIMode
import com.aireplyassistant.domain.model.AIRouterResponse
import com.aireplyassistant.domain.model.MessageContext

/**
 * ReplyGenerationRepository - Repository for reply generation operations.
 */
interface ReplyGenerationRepository {
    suspend fun generateReplies(
        messageContext: MessageContext,
        style: String,
        mode: AIMode
    ): AIRouterResponse
}
