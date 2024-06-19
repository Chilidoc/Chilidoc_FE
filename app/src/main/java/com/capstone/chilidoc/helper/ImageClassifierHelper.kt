package com.capstone.chilidoc.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.SystemClock
import android.view.Surface
import androidx.camera.core.ImageProxy
import com.capstone.chilidoc.R
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.InputStream

class ImageClassifierHelper(
    private var threshold: Float = 0.1f,
    private var maxResults: Int = 3,
    private val modelName: String = "model_with_metadata.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?,
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)

        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)

        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context, modelName, optionsBuilder.build()
            )
        } catch (e: IllegalStateException) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
        }
    }

    fun classifyImage(imageUri: Uri) {
        if (imageClassifier == null) {
            setupImageClassifier()
        }

        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)

        val results = imageClassifier?.classify(tensorImage)
        classifierListener?.onResults(results)
    }

//    fun classifyImage(image: ImageProxy) {
//        if (imageClassifier == null) {
//            setupImageClassifier()
//        }
//
//        val imageProcessor = ImageProcessor.Builder()
//            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
//            .add(CastOp(DataType.UINT8))
//            .build()
//
//        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(toBitmap(image)))
//
//        val imageProcessingOptions = ImageProcessingOptions.builder()
//            .setOrientation(getOrientationFromRotation(image.imageInfo.rotationDegrees))
//            .build()
//
//        var inferenceTime = SystemClock.uptimeMillis()
//        val results = imageClassifier?.classify(tensorImage, imageProcessingOptions)
//        inferenceTime = SystemClock.uptimeMillis() - inferenceTime
//        classifierListener?.onResults(results, inferenceTime)
//    }
//
//    private fun toBitmap(image: ImageProxy): Bitmap {
//        val bitmapBuffer = Bitmap.createBitmap(
//            image.width,
//            image.height,
//            Bitmap.Config.ARGB_8888
//        )
//        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
//        image.close()
//        return bitmapBuffer
//    }
//
//    private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation {
//        return when (rotation) {
//            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
//            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
//            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
//            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
//        }
//    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            results: List<Classifications>?,

        )
    }
}