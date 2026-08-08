package com.aireplyassistant.data.repository

import android.content.Context
import com.aireplyassistant.domain.repository.ChatGptConversationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatGptConversationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ChatGptConversationRepository {

    private val prefs = context.getSharedPreferences("chatgpt_prefs", Context.MODE_PRIVATE)
    private val keyUrl = "saved_url"

    private val _savedConversationUrl = MutableStateFlow<String?>(prefs.getString(keyUrl, null))
    override val savedConversationUrl: StateFlow<String?> = _savedConversationUrl.asStateFlow()

    override suspend fun saveUrl(url: String) {
        prefs.edit().putString(keyUrl, url).apply()
        _savedConversationUrl.value = url
    }

    override suspend fun clearUrl() {
        prefs.edit().remove(keyUrl).apply()
        _savedConversationUrl.value = null
    }
}
