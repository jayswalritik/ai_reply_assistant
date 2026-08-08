package com.aireplyassistant.presentation.accessibility.detector

import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.aireplyassistant.domain.model.MessageBubble
import javax.inject.Inject
import javax.inject.Singleton

/**
 * GenericTextBlockDetector - Specialized detector for non-messaging apps (like ChatGPT).
 * Focuses on extracting meaningful text blocks from any view hierarchy, including Compose and WebView.
 */
@Singleton
class GenericTextBlockDetector @Inject constructor() {

    private val exclusionKeywords = listOf("Copy", "Regenerate", "Send", "Share", "Like", "Dislike")

    /**
     * Detects meaningful text blocks in the given window root.
     */
    fun detect(root: AccessibilityNodeInfo): List<MessageBubble> {
        val textNodes = mutableListOf<Pair<AccessibilityNodeInfo, Rect>>()
        collectTextNodes(root, textNodes)

        // Group nodes by parent and proximity
        return textNodes
            .groupBy { it.first.parent?.hashCode() ?: -1 }
            .mapNotNull { (_, nodes) -> mergeNodesIntoBlock(nodes) }
            .filter { isValidBlock(it) }
    }

    private fun collectTextNodes(node: AccessibilityNodeInfo?, result: MutableList<Pair<AccessibilityNodeInfo, Rect>>) {
        if (node == null || !node.isVisibleToUser) return

        // Capture text from node (including merged Compose semantics)
        val text = node.text?.toString()?.trim() ?: node.contentDescription?.toString()?.trim() ?: ""
        
        if (text.isNotEmpty() && node.className != "android.widget.EditText") {
            val bounds = Rect()
            node.getBoundsInScreen(bounds)
            if (bounds.height() > 1 && bounds.width() > 1) {
                result.add(node to bounds)
            }
        }

        // Recurse into children
        for (i in 0 until node.childCount) {
            collectTextNodes(node.getChild(i), result)
        }
    }

    private fun mergeNodesIntoBlock(nodes: List<Pair<AccessibilityNodeInfo, Rect>>): MessageBubble? {
        val mergedText = nodes
            .mapNotNull { it.first.text?.toString() ?: it.first.contentDescription?.toString() }
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .trim()

        if (mergedText.isEmpty()) return null

        val bounds = Rect()
        nodes.forEach { (_, nodeRect) ->
            if (bounds.isEmpty) bounds.set(nodeRect) else bounds.union(nodeRect)
        }

        return MessageBubble(
            text = mergedText,
            isSentByUser = false,
            bounds = bounds
        )
    }

    private fun isValidBlock(bubble: MessageBubble): Boolean {
        if (bubble.text.length < 3) return false
        val lowercaseText = bubble.text.lowercase()
        return exclusionKeywords.none { it.lowercase() == lowercaseText }
    }
}
