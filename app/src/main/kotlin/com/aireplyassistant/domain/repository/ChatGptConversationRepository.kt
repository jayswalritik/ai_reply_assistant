package com.aireplyassistant.domain.repository

import kotlinx.coroutines.flow.StateFlow

/**
 * ChatGptConversationRepository - Handles storage of the persistent ChatGPT conversation URL.
 * Only the URL string is stored to ensure privacy compliance.
 */
interface ChatGptConversationRepository {
    
    /**
     * Flow of the currently saved ChatGPT conversation URL.
     */
    val savedConversationUrl: StateFlow<String?>

    /**
     * Save a new ChatGPT conversation URL.
     */
    suspend fun saveUrl(url: String)

    /**
     * Clear the saved conversation URL.
     */
    suspend fun clearUrl()
}
