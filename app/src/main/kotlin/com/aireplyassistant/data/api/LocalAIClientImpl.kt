package com.aireplyassistant.data.api

import com.aireplyassistant.data.api.model.OllamaGenerateRequest
import com.aireplyassistant.domain.router.LocalAIClient
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * LocalAIClientImpl - Highly optimized Ollama client.
 */
@Singleton
class LocalAIClientImpl @Inject constructor(
    private val ollamaApi: OllamaApiService
) : LocalAIClient {

    private val modelName = "llama3.2:3b"

    override suspend fun isAvailable(): Boolean = withContext(Dispatchers.IO) {
        try {
            ollamaApi.checkHealth().isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun generateReplies(
        message: String,
        style: String,
        context: List<String>
    ): List<String> = withContext(Dispatchers.IO) {
        try {
            val request = OllamaGenerateRequest(
                model = modelName,
                prompt = buildConcisePrompt(message, style, context),
                stream = false,
                options = mapOf(
                    "num_predict" to 100, // Limit output length for speed
                    "temperature" to 0.7,
                    "top_p" to 0.9
                )
            )

            val response = ollamaApi.generate(request)
            if (response.isSuccessful) {
                parseReplies(response.body()?.response ?: "")
            } else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun buildConcisePrompt(message: String, style: String, context: List<String>): String {
        val contextPart = if (context.isNotEmpty()) "Context: ${context.joinToString(" | ")}\n" else ""
        return """
            $contextPart
            Style: $style
            Reply to: "$message"
            Task: 5 short natural replies. One per line. No numbers.
            Replies:
        """.trimIndent()
    }

    private fun parseReplies(raw: String): List<String> {
        return raw.split("\n")
            .map { it.trim().removePrefix("- ").removePrefix("* ") }
            .filter { it.length in 2..60 } // Ignore very short or very long trash
            .take(5)
    }
}
