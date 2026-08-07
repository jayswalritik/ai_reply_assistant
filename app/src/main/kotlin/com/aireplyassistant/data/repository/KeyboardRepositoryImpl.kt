package com.aireplyassistant.data.repository

import com.aireplyassistant.domain.repository.KeyboardRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * KeyboardRepositoryImpl - Implementation of KeyboardRepository.
 *
 * Responsibilities:
 * - Implement keyboard data access operations
 * - Manage keyboard state (Phase 2: in-memory)
 * - Handle keyboard persistence (Phase 7: SharedPreferences)
 *
 * Phase 2 Implementation:
 * - All data is in-memory
 * - State is lost when app closes
 * - No persistence layer yet
 *
 * Phase 7 Enhancement:
 * - Add SharedPreferences for persistence
 * - Store keyboard preferences (layout, language, etc.)
 * - Cache recent suggestions (future)
 */
@Singleton
class KeyboardRepositoryImpl @Inject constructor() : KeyboardRepository {

    private var currentState: String = ""

    override suspend fun getCurrentKeyboardState(): String {
        return currentState
    }

    override suspend fun saveKeyboardState(state: String) {
        // Phase 2: Just store in memory
        currentState = state
        // Phase 7: Add SharedPreferences persistence
        // val prefs = context.getSharedPreferences("keyboard", Context.MODE_PRIVATE)
        // prefs.edit().putString("state", state).apply()
    }

    override suspend fun resetKeyboard() {
        currentState = ""
    }
}