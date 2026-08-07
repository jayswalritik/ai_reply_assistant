package com.aireplyassistant.presentation.accessibility

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.aireplyassistant.domain.model.MessageBubble
import com.aireplyassistant.domain.model.OverlayMode
import com.aireplyassistant.domain.repository.AccessibilityRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OverlaySelectionView(
    accessibilityRepository: AccessibilityRepository,
    mode: OverlayMode,
    onDismiss: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var allBubbles by remember { mutableStateOf<List<MessageBubble>>(emptyList()) }
    val selectedMessages = remember { mutableStateListOf<MessageBubble>() }
    var isScanning by remember { mutableStateOf(true) }
    var isScrollMode by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isScrollMode) {
        if (!isScrollMode) {
            isScanning = true
            
            // Wait for cache to be populated by service (max 2 seconds)
            var attempts = 0
            while (mode == OverlayMode.SINGLE_SELECT && attempts < 20) {
                val cached = accessibilityRepository.ocrCache.value
                if (cached.isNotEmpty()) {
                    Log.d("OverlayView", "OCR: Using cached results")
                    allBubbles = cached
                    accessibilityRepository.setOcrCache(emptyList())
                    break
                }
                delay(100)
                attempts++
            }

            if (allBubbles.isEmpty()) {
                Log.d("OverlayView", "OCR: Cache empty or timed out, performing fallback scan...")
                allBubbles = accessibilityRepository.scanVisibleBubbles(
                    isGeneric = mode == OverlayMode.SINGLE_SELECT,
                    useVisualOcr = mode == OverlayMode.SINGLE_SELECT
                )
            }
            isScanning = false
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (!isScrollMode) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
                    .pointerInput(allBubbles) {
                        detectTapGestures { offset ->
                            val tapped = allBubbles.find { it.bounds.contains(offset.x.toInt(), offset.y.toInt()) }
                            tapped?.let {
                                if (mode == OverlayMode.SINGLE_SELECT) {
                                    selectedMessages.clear()
                                    selectedMessages.add(it)
                                    // Copy to clipboard on select
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("AI Reply", it.text))
                                } else {
                                    if (selectedMessages.any { m -> m.text == it.text }) {
                                        selectedMessages.removeIf { m -> m.text == it.text }
                                    } else selectedMessages.add(it)
                                }
                            }
                        }
                    }
            ) {
                if (isScanning) {
                    Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Analyzing Screen...", color = Color.White)
                    }
                } else if (allBubbles.isEmpty()) {
                    Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "No text detected.",
                            color = Color.White,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            scope.launch {
                                isScanning = true
                                delay(300)
                                allBubbles = accessibilityRepository.scanVisibleBubbles(
                                    isGeneric = mode == OverlayMode.SINGLE_SELECT,
                                    useVisualOcr = mode == OverlayMode.SINGLE_SELECT
                                )
                                isScanning = false
                            }
                        }) {
                            Text("Retry Scan")
                        }
                    }
                }
                
                Canvas(modifier = Modifier.fillMaxSize()) {
                    allBubbles.forEach { bubble ->
                        val padding = 4.dp.toPx()
                        drawRect(
                            color = if (selectedMessages.any { it.text == bubble.text }) Color.Blue else Color.LightGray.copy(alpha = 0.5f),
                            topLeft = androidx.compose.ui.geometry.Offset(bubble.bounds.left.toFloat() - padding, bubble.bounds.top.toFloat() - padding),
                            size = androidx.compose.ui.geometry.Size(bubble.bounds.width().toFloat() + (padding * 2), bubble.bounds.height().toFloat() + (padding * 2)),
                            style = Stroke(width = if (selectedMessages.any { it.text == bubble.text }) 3.dp.toPx() else 1.dp.toPx())
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (mode == OverlayMode.SINGLE_SELECT) {
                Button(onClick = { isScrollMode = !isScrollMode }) {
                    Text(if (isScrollMode) "Scan Screen for Reply" else "Switch to Scroll Mode")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    accessibilityRepository.cancelSelection()
                    onDismiss()
                }) { Text("Cancel") }
                Button(
                    onClick = {
                        accessibilityRepository.confirmSelection(selectedMessages.toList())
                        onDismiss()
                    },
                    enabled = selectedMessages.isNotEmpty()
                ) {
                    val label = if (mode == OverlayMode.SINGLE_SELECT) "Confirm Selection" else "Generate (${selectedMessages.size})"
                    Text(label)
                }
            }
        }
    }
}
