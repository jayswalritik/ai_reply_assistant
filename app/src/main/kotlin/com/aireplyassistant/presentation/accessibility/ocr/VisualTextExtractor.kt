package com.aireplyassistant.presentation.accessibility.ocr

import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Log
import com.aireplyassistant.domain.model.MessageBubble
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * VisualTextExtractor - Uses Google ML Kit to extract text from a screen bitmap (OCR).
 * Provides "Samsung-like" text extraction with precise coordinate scaling.
 */
@Singleton
class VisualTextExtractor @Inject constructor() {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend fun extractText(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): List<MessageBubble> = withContext(Dispatchers.Default) {
        try {
            val scaleX = targetWidth.toFloat() / bitmap.width
            val scaleY = targetHeight.toFloat() / bitmap.height

            val image = InputImage.fromBitmap(bitmap, 0)
            val result = Tasks.await(recognizer.process(image))

            // Flatten every line from every block - don't trust ML Kit's block/paragraph
            // grouping, since it can split a single 2-3 line reply into separate blocks.
            data class ScaledLine(val text: String, val rect: Rect)
            val allLines = result.textBlocks
                .flatMap { it.lines }
                .filter { it.text.isNotBlank() }
                .map { ScaledLine(it.text, scaleRect(it.boundingBox ?: Rect(), scaleX, scaleY)) }
                .sortedBy { it.rect.top }

            // Merge lines that are vertically close and horizontally overlapping into one bubble
            val bubbles = mutableListOf<MessageBubble>()
            var currentGroup = mutableListOf<ScaledLine>()

            fun flushGroup() {
                if (currentGroup.isEmpty()) return
                val mergedText = currentGroup.joinToString(" ") { it.text }
                val mergedBounds = Rect(currentGroup.first().rect)
                currentGroup.forEach { mergedBounds.union(it.rect) }
                if (mergedText.length > 5) {
                    bubbles.add(MessageBubble(text = mergedText, isSentByUser = false, bounds = mergedBounds.apply { inset(-4, -4) }))
                }
                currentGroup = mutableListOf()
            }

            for (line in allLines) {
                val last = currentGroup.lastOrNull()
                if (last == null) {
                    currentGroup.add(line)
                    continue
                }
                val lineHeight = last.rect.height().coerceAtLeast(1)
                val verticalGap = line.rect.top - last.rect.bottom
                val horizontalOverlap = line.rect.left < last.rect.right && line.rect.right > last.rect.left
                if (verticalGap < lineHeight * 0.8 && horizontalOverlap) {
                    currentGroup.add(line)
                } else {
                    flushGroup()
                    currentGroup.add(line)
                }
            }
            flushGroup()

            Log.d("VisualExtractor", "OCR: Extracted ${bubbles.size} merged bubbles")
            bubbles.distinctBy { it.text }
        } catch (e: Exception) {
            Log.e("VisualExtractor", "OCR failed", e)
            emptyList()
        } finally {
            bitmap.recycle()
        }
    }

    private fun scaleRect(rect: Rect, scaleX: Float, scaleY: Float): Rect {
        val r = Rect(
            (rect.left * scaleX).toInt(),
            (rect.top * scaleY).toInt(),
            (rect.right * scaleX).toInt(),
            (rect.bottom * scaleY).toInt()
        )
        r.inset(-2, -2) // Tiny padding for visual wrap
        return r
    }
}
