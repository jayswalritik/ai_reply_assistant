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

            data class ScaledLine(val text: String, val rect: Rect)
            val bubbles = mutableListOf<MessageBubble>()

            // Hard split at ML Kit block boundaries (usually a real paragraph gap),
            // but within each block, only split further on an unusually large gap
            // between lines - that's what marks "next reply option" vs "next wrapped
            // line of the same reply."
            result.textBlocks.forEach { block ->
                val lines = block.lines
                    .filter { it.text.isNotBlank() }
                    .map { ScaledLine(it.text, scaleRect(it.boundingBox ?: Rect(), scaleX, scaleY)) }
                    .sortedBy { it.rect.top }

                var group = mutableListOf<ScaledLine>()
                val gaps = lines.zipWithNext { a, b -> b.rect.top - a.rect.bottom }
                val typicalGap = gaps.filter { it > 0 }.average().takeIf { !it.isNaN() } ?: 0.0

                fun flush() {
                    if (group.isEmpty()) return
                    val text = group.joinToString(" ") { it.text }
                    val bounds = Rect(group.first().rect)
                    group.forEach { bounds.union(it.rect) }
                    if (text.length > 5) {
                        bubbles.add(MessageBubble(text = text, isSentByUser = false, bounds = bounds.apply { inset(-4, -4) }))
                    }
                    group = mutableListOf()
                }

                lines.forEachIndexed { index, line ->
                    if (group.isEmpty()) {
                        group.add(line)
                    } else {
                        val gap = line.rect.top - group.last().rect.bottom
                        // Gap more than ~2x the typical line spacing = new reply option
                        if (typicalGap > 0 && gap > typicalGap * 2.2) {
                            flush()
                        }
                        group.add(line)
                    }
                }
                flush()
            }

            Log.d("VisualExtractor", "OCR: Extracted ${bubbles.size} bubbles")
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
