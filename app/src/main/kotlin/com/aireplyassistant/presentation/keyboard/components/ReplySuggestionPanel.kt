package com.aireplyassistant.presentation.keyboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * ReplySuggestionPanel - Displays AI-generated reply suggestions.
 *
 * Responsibilities:
 * - Show a list of 4-6 AI-generated reply suggestions
 * - Display loading indicator while generating replies
 * - Handle user selection of a reply
 * - Show visual distinction for suggested replies
 *
 * Layout:
 * ┌─────────────────────────────────────┐
 * │  AI Reply Suggestions               │
 * ├─────────────────────────────────────┤
 * │ [Reply 1 text...]                   │
 * │ [Reply 2 text...]                   │
 * │ [Reply 3 text...]                   │
 * │ [Reply 4 text...]                   │
 * │ [Reply 5 text...]                   │
 * │ [Reply 6 text...]                   │
 * └─────────────────────────────────────┘
 *
 * Parameters:
 * - isVisible: Whether to show the panel
 * - suggestions: List of reply strings (typically 4-6 items)
 * - onReplySelected: Callback when user taps a reply
 * - isLoading: Loading indicator
 *
 * Behavior:
 * 1. When AI button is pressed, panel becomes visible
 * 2. isLoading = true shows loading spinner
 * 3. When replies arrive, display them as clickable cards
 * 4. User taps a reply → onReplySelected is called
 * 5. Reply text is inserted into the message field
 * 6. Panel automatically hides after selection
 *
 * Visual Design:
 * - Background: Surface color with slight elevation
 * - Cards: Secondary color background
 * - Text: onSecondary color
 * - Selection feedback: Color change on tap
 *
 * Phase 2 Implementation:
 * - Component is ready and can display suggestions
 * - Phase 4 will integrate actual reply generation
 * - Will receive suggestions from AI Router
 *
 * Future Features:
 * - Regenerate button (regenerate with same context)
 * - Style selector (Funny, Casual, Friendly, etc.)
 * - Copy-to-clipboard functionality
 * - Reply preview before insertion
 */
@Composable
fun ReplySuggestionPanel(
    isVisible: Boolean,
    suggestions: List<String>,
    onReplySelected: (String) -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        ) {
            // Header
            Text(
                text = "AI Suggestions",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Loading or suggestions
            if (isLoading) {
                // Loading state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                // Show suggestions as clickable cards
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 250.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(suggestions) { suggestion ->
                        ReplySuggestionCard(
                            text = suggestion,
                            onClick = { onReplySelected(suggestion) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

/**
 * ReplySuggestionCard - Individual reply suggestion card.
 *
 * Displays a single reply suggestion with visual feedback on tap.
 */
@Composable
fun ReplySuggestionCard(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * PlaceholderReplySuggestionPanel - For Phase 2 testing.
 *
 * Shows how the panel will look with sample data.
 * Replace with actual ReplySuggestionPanel in Phase 3/4.
 */
@Composable
fun PlaceholderReplySuggestionPanel(modifier: Modifier = Modifier) {
    ReplySuggestionPanel(
        isVisible = true,
        suggestions = listOf(
            "That sounds great!",
            "I totally agree with you",
            "You're absolutely right about this",
            "I couldn't have said it better myself",
            "Haha, that's hilarious!",
            "Let me get back to you on that"
        ),
        onReplySelected = { },
        isLoading = false,
        modifier = modifier
    )
}