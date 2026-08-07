package com.aireplyassistant.domain.router

import com.aireplyassistant.domain.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRouter @Inject constructor(
    private val localAIClient: LocalAIClient,
    private val cloudAIService: CloudAIService
) {
    suspend fun generateReplies(request: AIRouterRequest, provider: AIProvider? = null): AIRouterResponse {
        val targetProvider = provider ?: AIProvider.LOCAL
        return when (targetProvider) {
            AIProvider.LOCAL -> generateWithLocal(request)
            AIProvider.GEMINI, AIProvider.GROQ -> generateWithCloud(request, targetProvider)
            AIProvider.CHATGPT -> AIRouterResponse.Unavailable // Handled via app-switch flow
        }
    }

    private suspend fun generateWithLocal(request: AIRouterRequest): AIRouterResponse {
        return if (localAIClient.isAvailable()) {
            try {
                val replies = localAIClient.generateReplies(
                    message = request.messageContext.latestMessage,
                    style = request.replyStyle,
                    context = request.messageContext.conversationHistory
                )
                AIRouterResponse.Success(replies)
            } catch (e: Exception) { AIRouterResponse.Error(e) }
        } else AIRouterResponse.Unavailable
    }

    private suspend fun generateWithCloud(request: AIRouterRequest, provider: AIProvider): AIRouterResponse {
        return try {
            val replies = cloudAIService.generateReplies(
                message = request.messageContext.latestMessage,
                style = request.replyStyle,
                context = request.messageContext.conversationHistory,
                provider = provider
            )
            AIRouterResponse.Success(replies)
        } catch (e: Exception) { AIRouterResponse.Error(e) }
    }
}

interface LocalAIClient {
    suspend fun isAvailable(): Boolean
    suspend fun generateReplies(message: String, style: String, context: List<String>): List<String>
}

interface CloudAIService {
    suspend fun generateReplies(message: String, style: String, context: List<String>, provider: AIProvider): List<String>
}
