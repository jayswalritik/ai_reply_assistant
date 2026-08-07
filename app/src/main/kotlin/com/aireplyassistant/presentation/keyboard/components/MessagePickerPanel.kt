package com.aireplyassistant.presentation.keyboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aireplyassistant.domain.model.MessageBubble

/**
 * MessagePickerPanel - UI for selecting which message to reply to.
 */
@Composable
fun MessagePickerPanel(
    messages: List<MessageBubble>,
    onMessageSelected: (MessageBubble) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Reply to...",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = "Close",
                modifier = Modifier.clickable { onDismiss() },
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (messages.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No messages found on screen", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(messages) { message ->
                    MessageBubbleItem(
                        message = message,
                        onClick = { onMessageSelected(message) }
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubbleItem(
    message: MessageBubble,
    onClick: () -> Unit
) {
    val alignment = if (message.isSentByUser) Alignment.End else Alignment.Start
    val bgColor = if (message.isSentByUser) 
        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f) 
    else 
        MaterialTheme.colorScheme.surfaceVariant

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (message.isSentByUser) 0.7f else 1.0f) // De-emphasize sent messages
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .align(alignment)
                .background(bgColor, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
