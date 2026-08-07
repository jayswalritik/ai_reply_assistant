package com.aireplyassistant.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * OllamaGenerateRequest - Request body for /api/generate
 */
data class OllamaGenerateRequest(
    val model: String,
    val prompt: String,
    val stream: Boolean = false,
    val options: Map<String, Any>? = null
)

/**
 * OllamaGenerateResponse - Response body for /api/generate (non-streaming)
 */
data class OllamaGenerateResponse(
    val model: String,
    @SerializedName("created_at") val createdAt: String,
    val response: String,
    val done: Boolean,
    @SerializedName("total_duration") val totalDuration: Long?,
    @SerializedName("load_duration") val loadDuration: Long?,
    @SerializedName("prompt_eval_count") val promptEvalCount: Int?,
    @SerializedName("eval_count") val evalCount: Int?
)

/**
 * OllamaTagsResponse - Response body for /api/tags
 */
data class OllamaTagsResponse(
    val models: List<OllamaModel>
)

/**
 * OllamaModel - Model details from /api/tags
 */
data class OllamaModel(
    val name: String,
    val modified_at: String,
    val size: Long,
    val digest: String
)
