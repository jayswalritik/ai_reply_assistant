package com.aireplyassistant.domain.usecase

/**
 * HandleKeyPressUseCase - Business logic for processing keyboard input.
 *
 * Responsibilities:
 * - Validate key input
 * - Process character input (letters, numbers, symbols)
 * - Handle special keys (backspace, space, enter)
 * - Filter out invalid characters
 * - Track input for accessibility context (Phase 3)
 *
 * Phase 2 Implementation:
 * - Simple pass-through logic for all valid characters
 * - No filtering or validation yet
 *
 * Future Enhancement (Phase 3+):
 * - Filter certain characters based on context
 * - Validate input length constraints
 * - Log input for accessibility service
 *
 * Example:
 * val useCase = HandleKeyPressUseCase()
 * useCase.execute('A') // Returns Result.Success('A')
 * useCase.execute('\b') // Returns Result.Success('\b') - backspace
 */
class HandleKeyPressUseCase {

    fun execute(keyChar: Char): Result<Char> {
        return try {
            // Phase 2: Accept all printable characters
            if (keyChar.code >= 32) {  // Printable ASCII
                Result.success(keyChar)
            } else {
                Result.failure(IllegalArgumentException("Invalid character: $keyChar"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}