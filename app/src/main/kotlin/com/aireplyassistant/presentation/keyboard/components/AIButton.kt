package com.aireplyassistant.presentation.keyboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * AIButton - The AI suggestion button for the keyboard.
 *
 * Responsibilities:
 * - Display the 🤖 (robot/AI) emoji button
 * - Trigger AI reply generation when pressed
 * - Provide visual distinction from regular keys
 * - Indicate when suggestions are loading
 *
 * Visual Design:
 * - Distinct color: Primary color (different from other keys)
 * - Emoji: 🤖 (robot/AI symbol)
 * - Accessible: Large enough for reliable touch
 * - Feedback: Color change on press
 *
 * Interaction:
 * 1. User taps the 🤖 button
 * 2. onClick callback is triggered
 * 3. Phase 3: Accessibility service captures message context
 * 4. Phase 4: AI Router generates replies
 * 5. Reply suggestions are displayed below keyboard
 *
 * Phase 2 Implementation:
 * - Button is rendered with primary color
 * - Click handler is set up
 * - Visual feedback on tap
 * - Placeholder for Phase 3/4 integration
 *
 * Future Enhancements:
 * - Loading animation while generating replies
 * - Disable button if no message context available
 * - Tooltip: "Tap for AI suggestions"
 */
@Composable
fun AIButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
            )
            .clickable(onClick = onClick)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🤖",
            fontSize = 20.sp
        )
    }
}