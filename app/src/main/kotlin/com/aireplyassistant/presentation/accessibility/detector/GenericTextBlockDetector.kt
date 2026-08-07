package com.aireplyassistant.presentation.accessibility.detector

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import com.aireplyassistant.domain.model.MessageBubble
import javax.inject.Inject
import javax.inject.Singleton

/**
 * GenericTextBlockDetector - Specialized detector for non-messaging apps (like ChatGPT).
 * Focuses on extracting meaningful text blocks from any view hierarchy or WebView.
 */
@Singleton
class GenericTextBlockDetector @Inject constructor() {

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
            .filter { it.text.length > 10 } // Filter out UI noise (buttons, labels)
    }

    private fun collectTextNodes(node: AccessibilityNodeInfo?, result: MutableList<Pair<AccessibilityNodeInfo, Rect>>) {
        if (node == null || !node.isVisibleToUser) return

        val text = node.text?.toString()?.trim() ?: node.contentDescription?.toString()?.trim() ?: ""
        
        // Include WebView content by checking generic class names or nodes with text
        if (text.isNotEmpty() && node.className != "android.widget.EditText") {
            val bounds = Rect()
            node.getBoundsInScreen(bounds)
            if (bounds.height() > 1 && bounds.width() > 1) {
                result.add(node to bounds)
            }
        }

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
        
        // Add padding for better "wrapping" visuals
        bounds.inset(-4, -4)

        return MessageBubble(
            text = mergedText,
            isSentByUser = false, // In generic mode, we just care about capturing the text
            bounds = bounds
        )
    }
}
