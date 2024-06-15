package com.capstone.chilidoc.helper

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.capstone.chilidoc.R
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.InputStream

class ImageClassifierHelper(
    private var threshold: Float = 0.1f,
    private var maxResults: Int = 3,
    private val modelName: String = "model.tflite",
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

    fun classifyStaticImage(imageUri: Uri) {
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

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            results: List<Classifications>?,
        )
    }
}