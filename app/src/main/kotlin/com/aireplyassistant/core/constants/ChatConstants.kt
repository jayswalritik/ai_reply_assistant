package com.aireplyassistant.core.constants

/**
 * ChatConstants - Centralized patterns for filtering chat UI noise.
 */
object ChatConstants {
    /**
     * Keywords that usually indicate non-message UI elements.
     */
    val FILTER_KEYWORDS = listOf(
        "seen", "delivered", "typing", "call", "today", "yesterday",
        "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday",
        "active now", "online", "write a message", "type a message", "search"
    )

    /**
     * Regex for common time formats (e.g., 2:30 PM, 14:20).
     */
    val TIME_PATTERN = Regex("^\\d{1,2}:\\d{2}\\s?(AM|PM)?$", RegexOption.IGNORE_CASE)

    /**
     * Packages to ignore completely.
     */
    val SYSTEM_PACKAGES = listOf("com.android.systemui", "android", "com.aireplyassistant")
}
