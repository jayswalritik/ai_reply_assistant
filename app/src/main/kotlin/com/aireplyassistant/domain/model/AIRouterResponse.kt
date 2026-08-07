package com.aireplyassistant.domain.model

sealed class AIRouterResponse {
    data class Success(val replies: List<String>) : AIRouterResponse()
    data class Error(val exception: Exception) : AIRouterResponse()
    object Unavailable : AIRouterResponse()
}
