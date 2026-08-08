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
            
            Log.d("VisualExtractor", "Screen: ${targetWidth}x${targetHeight}, Bitmap: ${bitmap.width}x${bitmap.height}, Scale: $scaleX, $scaleY")
            
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
                
                // Add individual lines for finer selection in long replies
                if (block.lines.size > 1) {
                    block.lines.forEach { line ->
                        if (line.text.length > 3 && line.text != block.text) {
                            bubbles.add(MessageBubble(
                                text = line.text,
                                isSentByUser = false,
                                bounds = scaleRect(line.boundingBox ?: Rect(), scaleX, scaleY)
                            ))
                        }
                    }
                }
            }
            
            Log.d("VisualExtractor", "OCR: Found ${bubbles.size} bubbles")
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
