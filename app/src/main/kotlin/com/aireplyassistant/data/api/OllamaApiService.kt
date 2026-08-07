package com.aireplyassistant.data.api

import com.aireplyassistant.data.api.model.OllamaGenerateRequest
import com.aireplyassistant.data.api.model.OllamaGenerateResponse
import com.aireplyassistant.data.api.model.OllamaTagsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Tag

/**
 * OllamaApiService - Retrofit interface for Ollama local AI server.
 */
interface OllamaApiService {

    /**
     * Generate text based on a prompt.
     */
    @POST("/api/generate")
    suspend fun generate(@Body request: OllamaGenerateRequest): Response<OllamaGenerateResponse>

    /**
     * List local models.
     */
    @GET("/api/tags")
    suspend fun getModels(): Response<OllamaTagsResponse>

    /**
     * Simple health check.
     */
    @GET("/")
    suspend fun checkHealth(): Response<Unit>
}
