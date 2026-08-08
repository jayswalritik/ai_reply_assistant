package com.aireplyassistant.presentation.keyboard.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aireplyassistant.domain.model.AIProvider

/**
 * AIProviderMenu - Bottom sheet menu for selecting AI backend.
 * Support long-press on ChatGPT to reset its saved conversation.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AIProviderMenu(
    onProviderSelected: (AIProvider) -> Unit,
    onResetChatGPT: () -> Unit,
    onDismiss: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Select AI Provider", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                IconButton(onClick = onDismiss) {
                    Text("Close", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn {
                items(AIProvider.entries) { provider ->
                    ProviderItem(
                        provider = provider, 
                        onClick = { onProviderSelected(provider) },
                        onLongClick = {
                            if (provider == AIProvider.CHATGPT) onResetChatGPT()
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProviderItem(provider: AIProvider, onClick: () -> Unit, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(provider.icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(provider.displayName, fontSize = 14.sp)
        }
    }
}
