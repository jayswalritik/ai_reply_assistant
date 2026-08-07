package com.aireplyassistant.domain.repository

/**
 * KeyboardRepository - Repository interface for keyboard operations.
 *
 * Responsibilities:
 * - Abstract keyboard operations
 * - Provide data access layer for keyboard state
 * - Handle keyboard preferences (future: stored settings)
 *
 * This is the data layer interface. Implementations will:
 * - Store keyboard preferences
 * - Cache keyboard history (future)
 * - Access keyboard settings (future)
 *
 * Clean Architecture:
 * Domain layer depends on this interface (abstract)
 * Data layer provides the implementation (concrete)
 */
interface KeyboardRepository {

    /**
     * Get current keyboard state (if any is persisted).
     * Phase 2: Returns empty string
     * Phase 7: Will return saved keyboard settings
     */
    suspend fun getCurrentKeyboardState(): String

    /**
     * Save keyboard state or preferences.
     * Phase 2: No-op
     * Phase 7: Will save to SharedPreferences
     */
    suspend fun saveKeyboardState(state: String)

    /**
     * Reset keyboard to default state.
     */
    suspend fun resetKeyboard()
}