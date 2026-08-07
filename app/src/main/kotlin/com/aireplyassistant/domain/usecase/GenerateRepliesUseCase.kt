package com.aireplyassistant.domain.usecase

import com.aireplyassistant.domain.model.*
import com.aireplyassistant.domain.router.AIRouter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenerateRepliesUseCase @Inject constructor(
    private val aiRouter: AIRouter
) {
    suspend fun execute(
        messageContext: MessageContext,
        style: String,
        mode: AIMode = AIMode.ADAPTIVE,
        provider: AIProvider? = null
    ): AIRouterResponse {
        return try {
            val request = AIRouterRequest(
                messageContext = messageContext,
                replyStyle = style,
                mode = mode
            )
            aiRouter.generateReplies(request, provider)
        } catch (e: Exception) {
            AIRouterResponse.Error(e)
        }
    }
}
