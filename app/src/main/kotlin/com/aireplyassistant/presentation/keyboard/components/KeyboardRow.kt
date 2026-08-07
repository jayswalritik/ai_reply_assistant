package com.aireplyassistant.presentation.keyboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * KeyboardKeyData - Data class representing a key in the keyboard.
 *
 * @param label The text displayed on the key.
 * @param weight The relative width of the key in the row.
 * @param isSpecial Whether the key is a special key (e.g., Backspace, Enter).
 * @param backgroundColor Optional custom background color.
 * @param contentColor Optional custom content color.
 * @param icon Optional composable to display an icon instead of text.
 * @param onClick Callback when the key is pressed.
 */
data class KeyboardKeyData(
    val label: String,
    val weight: Float = 1f,
    val isSpecial: Boolean = false,
    val backgroundColor: Color? = null,
    val contentColor: Color? = null,
    val icon: (@Composable () -> Unit)? = null,
    val onClick: () -> Unit,
    val onLongPress: (() -> Unit)? = null,
    val isImmediate: Boolean = true // Trigger onClick on DOWN if true
)

/**
 * KeyboardKey - Component for rendering a single keyboard key.
 *
 * @param label The text displayed on the key.
 * @param onClick Callback when the key is pressed.
 * @param modifier Layout modifier for the key.
 * @param isSpecialKey Whether the key is a special key.
 * @param backgroundColor Optional custom background color.
 * @param contentColor Optional custom content color.
 * @param icon Optional composable to display an icon.
 */
@Composable
fun KeyboardKey(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSpecialKey: Boolean = false,
    backgroundColor: Color? = null,
    contentColor: Color? = null,
    icon: (@Composable () -> Unit)? = null,
    onLongPress: (() -> Unit)? = null,
    isImmediate: Boolean = true
) {
    val finalBackgroundColor = backgroundColor ?: if (isSpecialKey) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val finalContentColor = contentColor ?: if (isSpecialKey) {
        MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = modifier
            .padding(horizontal = 2.dp, vertical = 3.dp)
            .background(
                color = finalBackgroundColor,
                shape = RoundedCornerShape(5.dp)
            )
            .pointerInput(onClick, onLongPress, isImmediate) {
                awaitEachGesture {
                    awaitFirstDown()
                    
                    if (isImmediate) {
                        // For letters and backspace: Trigger immediately
                        onClick()
                        
                        if (onLongPress != null) {
                            // Wait for initial delay
                            val up = withTimeoutOrNull(500) {
                                waitForUpOrCancellation()
                            }
                            
                            if (up == null) {
                                // Start repeating (standard for backspace)
                                while (true) {
                                    onLongPress()
                                    val repeatUp = withTimeoutOrNull(50) {
                                        waitForUpOrCancellation()
                                    }
                                    if (repeatUp != null) break
                                }
                            }
                        } else {
                            waitForUpOrCancellation()
                        }
                    } else {
                        // For keys like Comma: Distinguish between tap and long press
                        var longPressed = false
                        if (onLongPress != null) {
                            val up = withTimeoutOrNull(500) {
                                waitForUpOrCancellation()
                            }
                            
                            if (up == null) {
                                // Long press triggered
                                onLongPress()
                                longPressed = true
                                waitForUpOrCancellation() // Wait for release
                            } else {
                                // Tap (UP before timeout)
                                onClick()
                            }
                        } else {
                            onClick()
                            waitForUpOrCancellation()
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (icon != null) {
            icon()
        } else {
            Text(
                text = label,
                color = finalContentColor,
                fontSize = 18.sp, // Larger font for better readability
                maxLines = 1
            )
        }
    }
}

/**
 * KeyboardRow - Component for rendering a horizontal row of keyboard keys.
 *
 * @param keys List of KeyboardKeyData objects to render.
 * @param modifier Layout modifier for the row.
 */
@Composable
fun KeyboardRow(
    keys: List<KeyboardKeyData>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp) // Reduced height to feel more compact and "smooth"
            .padding(horizontal = 1.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        keys.forEach { keyData ->
            KeyboardKey(
                label = keyData.label,
                onClick = keyData.onClick,
                onLongPress = keyData.onLongPress,
                isImmediate = keyData.isImmediate,
                isSpecialKey = keyData.isSpecial,
                backgroundColor = keyData.backgroundColor,
                contentColor = keyData.contentColor,
                icon = keyData.icon,
                modifier = Modifier
                    .weight(keyData.weight)
                    .fillMaxHeight()
            )
        }
    }
}
