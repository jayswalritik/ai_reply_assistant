package com.aireplyassistant.data.api

import com.aireplyassistant.data.api.model.*
import com.aireplyassistant.domain.model.AIProvider
import com.aireplyassistant.domain.router.CloudAIService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudAIServiceImpl @Inject constructor(
    private val geminiApi: GeminiApiService,
    private val groqApi: GroqApiService
) : CloudAIService {

    override suspend fun generateReplies(
        message: String,
        style: String,
        context: List<String>,
        provider: AIProvider
    ): List<String> {
        val transcript = buildTranscript(message, context)
        val prompt = "Here is a conversation between my friend and me:\n\n$transcript\n\nWrite distinct natural replies I could send next in a $style style. Provide shorter reply. Do not use labels or numbers."

        return when (provider) {
            AIProvider.GEMINI -> callGemini(prompt)
            AIProvider.GROQ -> callGroq(prompt)
            else -> emptyList()
        }
    }

    private suspend fun callGemini(prompt: String): List<String> {
        val apiKey = "" // TODO: Get from settings
        if (apiKey.isEmpty()) return listOf("Gemini API key missing", "Go to Settings")
        
        val request = GeminiRequest(contents = listOf(Content(parts = listOf(Part(text = prompt)))))
        val response = geminiApi.generateContent(apiKey, request)
        return if (response.isSuccessful) {
            response.body()?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?.split("\n")?.filter { it.isNotBlank() }?.take(5) ?: emptyList()
        } else emptyList()
    }

    private suspend fun callGroq(prompt: String): List<String> {
        val apiKey = "" // TODO: Get from settings
        if (apiKey.isEmpty()) return listOf("Groq API key missing", "Go to Settings")
        
        val request = GroqRequest(
            model = "llama3-70b-8192",
            messages = listOf(GroqMessage(role = "user", content = prompt))
        )
        // Note: Retrofit interceptor should add "Authorization: Bearer $apiKey"
        val response = groqApi.createChatCompletion(request)
        return if (response.isSuccessful) {
            response.body()?.choices?.firstOrNull()?.message?.content
                ?.split("\n")?.filter { it.isNotBlank() }?.take(5) ?: emptyList()
        } else emptyList()
    }

    private fun buildTranscript(message: String, context: List<String>): String {
        val sb = StringBuilder()
        context.forEachIndexed { index, msg ->
            val sender = if (index % 2 == 0) "My friend" else "Me"
            sb.append("$sender: $msg\n")
        }
        sb.append("My friend: $message")
        return sb.toString()
    }
}
