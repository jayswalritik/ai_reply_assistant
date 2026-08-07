package com.aireplyassistant.domain.model

/**
 * AIMode - Determines which AI provider to use for reply generation.
 *
 * Modes:
 * - ADAPTIVE: Try local, fall back to cloud if unavailable
 * - LOCAL_ONLY: Only use local PC (Ollama)
 * - CLOUD_ONLY: Always use cloud API
 */
enum class AIMode {
    ADAPTIVE,
    LOCAL_ONLY,
    CLOUD_ONLY
}
