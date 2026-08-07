package com.aireplyassistant.domain.model

/**
 * AIProvider - Represents the available AI processing backends.
 */
enum class AIProvider(val displayName: String, val icon: String) {
    LOCAL("Local (Ollama)", "🏠"),
    GEMINI("Gemini (API)", "✨"),
    GROQ("Groq (API)", "⚡"),
    CHATGPT("ChatGPT (App)", "💬")
}

/**
 * OverlayMode - Defines how the selection overlay behaves.
 */
enum class OverlayMode {
    MULTI_SELECT, // Build conversation context
    SINGLE_SELECT // Capture one specific reply (e.g. from ChatGPT)
}
