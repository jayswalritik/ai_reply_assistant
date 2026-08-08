package com.aireplyassistant.presentation.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.aireplyassistant.data.repository.AccessibilityRepositoryImpl
import com.aireplyassistant.domain.model.MessageBubble
import com.aireplyassistant.domain.model.OverlayMode
import com.aireplyassistant.domain.repository.AccessibilityRepository
import com.aireplyassistant.presentation.accessibility.detector.GenericTextBlockDetector
import com.aireplyassistant.presentation.accessibility.ocr.VisualTextExtractor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.concurrent.Executor
import javax.inject.Inject
import kotlin.coroutines.resume

@AndroidEntryPoint
class AIAccessibilityService : AccessibilityService() {

    @Inject
    lateinit var accessibilityRepository: AccessibilityRepository

    @Inject
    lateinit var visualTextExtractor: VisualTextExtractor

    @Inject
    lateinit var genericTextBlockDetector: GenericTextBlockDetector

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onServiceConnected() {
        super.onServiceConnected()
        (accessibilityRepository as? AccessibilityRepositoryImpl)?.setScanBubblesProvider { isGeneric, useOcr ->
            if (useOcr && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                performOcrScan()
            } else {
                scanVisibleBubbles(isGeneric)
            }
        }

        (accessibilityRepository as? AccessibilityRepositoryImpl)?.setCaptureUrlProvider {
            captureBrowserUrl()
        }
        
        serviceScope.launch {
            accessibilityRepository.overlayVisibilityRequests.collect { (visible, mode) ->
                Log.d("AIAccessibility", "Overlay Visibility: $visible, Mode: $mode")
                
                if (visible && mode == OverlayMode.SINGLE_SELECT) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Log.d("AIAccessibility", "Settling UI before OCR screenshot...")
                        delay(200) // Give UI time to settle after keyboard hide
                        val bubbles = performOcrScan()
                        if (bubbles.isNotEmpty()) {
                            Log.d("AIAccessibility", "OCR Success: ${bubbles.size} bubbles cached")
                            accessibilityRepository.setOcrCache(bubbles)
                        } else {
                            Log.e("AIAccessibility", "OCR Failed: No text detected in screenshot")
                        }
                    } else {
                        Log.w("AIAccessibility", "OCR skipped: Requires Android 11+")
                    }
                }
                
                val intent = Intent(this@AIAccessibilityService, SelectionOverlayService::class.java).apply {
                    putExtra("mode", mode.name)
                }
                if (visible) startService(intent) else stopService(intent)
            }
        }
    }

    private suspend fun performOcrScan(): List<MessageBubble> = suspendCancellableCoroutine { continuation ->
        Log.d("AIAccessibility", "OCR: Initiating screenshot capture...")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                takeScreenshot(Display.DEFAULT_DISPLAY, applicationContext.mainExecutor, object : TakeScreenshotCallback {
                    override fun onSuccess(screenshot: ScreenshotResult) {
                        val buffer = screenshot.hardwareBuffer
                        val bitmap = android.graphics.Bitmap.wrapHardwareBuffer(buffer, screenshot.colorSpace)
                        buffer.close() // Close buffer immediately to prevent leaks

                        if (bitmap != null) {
                            Log.d("AIAccessibility", "Screenshot Bitmap: ${bitmap.width}x${bitmap.height}, Screen: ${resources.displayMetrics.widthPixels}x${resources.displayMetrics.heightPixels}")
                            
                            // ML Kit does NOT support Hardware Bitmaps. Must copy to software bitmap.
                            val softwareBitmap = bitmap.copy(android.graphics.Bitmap.Config.ARGB_8888, false)
                            bitmap.recycle()

                            serviceScope.launch {
                                Log.d("AIAccessibility", "OCR: Processing bitmap...")
                                
                                val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
                                val screenBounds = windowManager.currentWindowMetrics.bounds
                                val realWidth = screenBounds.width()
                                val realHeight = screenBounds.height()
                                
                                Log.d("AIAccessibility", "Real screen metrics: ${realWidth}x$realHeight")

                                val bubbles = visualTextExtractor.extractText(
                                    softwareBitmap,
                                    realWidth,
                                    realHeight
                                )
                                if (bubbles.isEmpty()) {
                                    Log.d("AIAccessibility", "OCR found nothing, falling back to tree scan")
                                    continuation.resume(scanVisibleBubbles(true))
                                } else {
                                    Log.d("AIAccessibility", "OCR: Found ${bubbles.size} bubbles")
                                    continuation.resume(bubbles)
                                }
                            }
                        } else {
                            Log.e("AIAccessibility", "OCR: Hardware bitmap wrapping failed")
                            continuation.resume(emptyList())
                        }
                    }

                    override fun onFailure(errorCode: Int) {
                        Log.e("AIAccessibility", "OCR: takeScreenshot failed code $errorCode")
                        continuation.resume(emptyList())
                    }
                })
            } catch (e: Exception) {
                Log.e("AIAccessibility", "OCR: takeScreenshot crashed", e)
                continuation.resume(emptyList())
            }
        } else {
            Log.w("AIAccessibility", "OCR: Requires Android 11+")
            continuation.resume(emptyList())
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val pkg = event.packageName?.toString() ?: ""
            if (pkg.isNotEmpty() && pkg != "com.aireplyassistant" && pkg != "com.android.systemui" && pkg != "android") {
                accessibilityRepository.setTargetPackage(pkg)
            }
        }
    }

    override fun onInterrupt() {}

    private fun captureBrowserUrl(): String? {
        val root = rootInActiveWindow ?: return null
        val candidates = mutableListOf<String>()
        findUrlInNodes(root, candidates)
        // Find first candidate that looks like chatgpt.com/c/...
        return candidates.find { it.contains("chatgpt.com/c/") } ?: candidates.firstOrNull()
    }

    private fun findUrlInNodes(node: AccessibilityNodeInfo?, result: MutableList<String>) {
        if (node == null) return
        val text = node.text?.toString() ?: ""
        if (text.contains("chatgpt.com")) {
            result.add(text)
        }
        for (i in 0 until node.childCount) {
            findUrlInNodes(node.getChild(i), result)
        }
    }

    private fun scanVisibleBubbles(isGeneric: Boolean): List<MessageBubble> {
        val windowList = windows
        val allBubbles = mutableListOf<MessageBubble>()
        val screenWidth = resources.displayMetrics.widthPixels
        val systemPackages = listOf("com.android.systemui", "android", "com.aireplyassistant", "com.google.android.inputmethod.latin")

        for (window in windowList) {
            val rootNode = window.root ?: continue
            val pkg = rootNode.packageName?.toString() ?: ""
            if (pkg.isEmpty() || systemPackages.contains(pkg)) continue
            
            val windowBubbles = if (isGeneric) {
                genericTextBlockDetector.detect(rootNode)
            } else {
                val textNodes = mutableListOf<Pair<AccessibilityNodeInfo, Rect>>()
                findTextNodesAggressive(rootNode, textNodes)
                textNodes.groupBy { it.first.parent?.hashCode() ?: -1 }
                    .mapNotNull { (_, nodes) -> createBubbleFromNodes(nodes, screenWidth) }
            }
            allBubbles.addAll(windowBubbles)
        }

        if (allBubbles.isEmpty()) {
            rootInActiveWindow?.let { root ->
                val bubbles = if (isGeneric) genericTextBlockDetector.detect(root) else {
                    val textNodes = mutableListOf<Pair<AccessibilityNodeInfo, Rect>>()
                    findTextNodesAggressive(root, textNodes)
                    textNodes.groupBy { it.first.parent?.hashCode() ?: -1 }
                        .mapNotNull { (_, nodes) -> createBubbleFromNodes(nodes, screenWidth) }
                }
                allBubbles.addAll(bubbles)
            }
        }
        return allBubbles.distinctBy { it.text }.sortedBy { it.bounds.top }
    }

    private fun createBubbleFromNodes(nodes: List<Pair<AccessibilityNodeInfo, Rect>>, screenWidth: Int): MessageBubble? {
        val mergedText = nodes
            .mapNotNull { it.first.text?.toString() ?: it.first.contentDescription?.toString() }
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .trim()
            
        if (mergedText.length < 2) return null
        
        val ignoreList = listOf("Type a message", "Search", "Messenger", "WhatsApp", "Active now", "Online", "Write a message")
        if (ignoreList.any { it.equals(mergedText, ignoreCase = true) }) return null

        val bounds = Rect()
        nodes.forEach { (_, nodeRect) -> 
            if (bounds.isEmpty) bounds.set(nodeRect) else bounds.union(nodeRect) 
        }
        
        return MessageBubble(
            text = mergedText,
            isSentByUser = bounds.centerX() > (screenWidth / 2),
            bounds = bounds
        )
    }

    private fun findTextNodesAggressive(node: AccessibilityNodeInfo?, result: MutableList<Pair<AccessibilityNodeInfo, Rect>>) {
        if (node == null) return
        val text = node.text?.toString()?.trim() ?: node.contentDescription?.toString()?.trim() ?: ""
        if (text.isNotEmpty() && node.className != "android.widget.EditText") {
            val bounds = Rect()
            node.getBoundsInScreen(bounds)
            if (bounds.height() > 1 && bounds.width() > 1) {
                result.add(node to bounds)
            }
        }
        for (i in 0 until node.childCount) findTextNodesAggressive(node.getChild(i), result)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
