package com.example.smartshopping2.domain.classify

import android.app.Activity
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorOperator
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class Classifier(
    activity : Activity,
    modelPath : String,
    labelPath : String
) {
    private val tfOptions = Interpreter.Options()
    private var tflite :Interpreter
    private var labels : List<String>
    private var inputImageBuffer : TensorImage
    private var outputProbabilityBuffer : TensorBuffer
    private var imageSizeX : Int
    private var imageSizeY : Int

    init {
        //tfOptions.setUseNNAPI(true)

        val tfModel = FileUtil.loadMappedFile(activity,modelPath)
        tflite = Interpreter(tfModel,tfOptions)

        labels = FileUtil.loadLabels(activity,labelPath)

        val imageShape = tflite.getInputTensor(0).shape()
        imageSizeY = imageShape[1]
        imageSizeX = imageShape[2]
        val imageDataType = tflite.getInputTensor(0).dataType()

        inputImageBuffer = TensorImage(imageDataType)

        val probShape = tflite.getOutputTensor(0).shape()
        val probDataType = tflite.getOutputTensor(0).dataType()

        outputProbabilityBuffer = TensorBuffer.createFixedSize(probShape,probDataType)
    }

    fun recognizeImage(bitmap: Bitmap) : List<Recognition>{
        inputImageBuffer = loadImage(bitmap)
        val probabilityProcessor = TensorProcessor.Builder().add(getPostProcessNormalizeOp()).build()
        tflite.run(inputImageBuffer.buffer,outputProbabilityBuffer.buffer.rewind())

        val tLabel = TensorLabel(labels,probabilityProcessor.process(outputProbabilityBuffer))
        val labelProb = tLabel.mapWithFloatValue

        return getSortedResult(labelProb)
    }


    private fun getPreProcessNormalizeOp() : TensorOperator = NormalizeOp(IMAGE_MEAN, IMAGE_STD)
    private fun getPostProcessNormalizeOp() : TensorOperator = NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD)

    private fun loadImage(bitmap: Bitmap) : TensorImage{
        inputImageBuffer.load(bitmap)

        val cropSize = bitmap.width.coerceAtMost(bitmap.height)
        val imageProcessor =
            ImageProcessor.Builder()
                .add(ResizeWithCropOrPadOp(cropSize,cropSize))
                .add(ResizeOp(imageSizeX,imageSizeY,ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                .add(getPreProcessNormalizeOp())
                .build()

        return imageProcessor.process(inputImageBuffer)
    }

    private fun loadModelFile(activity: Activity) : MappedByteBuffer{
        val fileDescriptor = activity.assets.openFd("model.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun getSortedResult(map : Map<String,Float>) =
        map.map { Recognition(it.key,it.value) }
            .sortedByDescending { it.confidence }
            .foldIndexed(arrayListOf<Recognition>()){ index, acc, recognition ->
                if (index <= MAX_RESULT) acc.add(recognition)
                acc
            }.toList()

    companion object{
        const val IMAGE_MEAN = 0.0f
        const val IMAGE_STD = 1.0f
        const val PROBABILITY_MEAN = 0.0f
        const val PROBABILITY_STD = 255.0f
        const val MAX_RESULT = 20
    }

    data class Recognition(
        val title : String,
        val confidence : Float
    )
}