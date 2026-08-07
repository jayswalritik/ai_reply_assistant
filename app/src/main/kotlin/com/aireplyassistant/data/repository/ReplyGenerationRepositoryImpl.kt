package com.aireplyassistant.data.repository

import com.aireplyassistant.domain.model.AIMode
import com.aireplyassistant.domain.model.AIRouterResponse
import com.aireplyassistant.domain.model.MessageContext
import com.aireplyassistant.domain.repository.ReplyGenerationRepository
import com.aireplyassistant.domain.usecase.GenerateRepliesUseCase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ReplyGenerationRepositoryImpl - Implementation of reply generation repository.
 */
@Singleton
class ReplyGenerationRepositoryImpl @Inject constructor(
    private val generateRepliesUseCase: GenerateRepliesUseCase
) : ReplyGenerationRepository {

    override suspend fun generateReplies(
        messageContext: MessageContext,
        style: String,
        mode: AIMode
    ): AIRouterResponse {
        return generateRepliesUseCase.execute(messageContext, style, mode)
    }
}
