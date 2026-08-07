package com.aireplyassistant.presentation.accessibility.ocr

import android.graphics.Bitmap
import android.graphics.Rect
import com.aireplyassistant.domain.model.MessageBubble
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * VisualTextExtractor - Uses Google ML Kit to extract text from a screen bitmap (OCR).
 * Provides "Samsung-like" text extraction for apps like ChatGPT.
 */
@Singleton
class VisualTextExtractor @Inject constructor() {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend fun extractText(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): List<MessageBubble> = withContext(Dispatchers.Default) {
        try {
            val scaleX = targetWidth.toFloat() / bitmap.width
            val scaleY = targetHeight.toFloat() / bitmap.height
            
            Log.d("VisualExtractor", "Analyzing bitmap: ${bitmap.width}x${bitmap.height}, Target: ${targetWidth}x${targetHeight}, Scale: $scaleX, $scaleY")
            val image = InputImage.fromBitmap(bitmap, 0)
            val result = Tasks.await(recognizer.process(image))
            
            val bubbles = mutableListOf<MessageBubble>()
            
            result.textBlocks.forEach { block ->
                val scaledRect = scaleRect(block.boundingBox ?: Rect(), scaleX, scaleY)
                if (block.text.length > 5) {
                    bubbles.add(MessageBubble(
                        text = block.text,
                        isSentByUser = false,
                        bounds = scaledRect
                    ))
                }
                if (block.lines.size > 2) {
                    block.lines.forEach { line ->
                        if (line.text.length > 5 && line.text != block.text) {
                            bubbles.add(MessageBubble(
                                text = line.text,
                                isSentByUser = false,
                                bounds = scaleRect(line.boundingBox ?: Rect(), scaleX, scaleY)
                            ))
                        }
                    }
                }
            }
            
            Log.d("VisualExtractor", "OCR: Extracted ${bubbles.size} potential bubbles")
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
        // Add a tiny bit of padding to the bounds for better "wrapping" visuals
        r.inset(-2, -2)
        return r
    }
}
