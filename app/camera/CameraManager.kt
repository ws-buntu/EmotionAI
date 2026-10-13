package com.emotionai.camera

import android.content.Context
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner

class CameraManager(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private  val analyzer: ImageAnalysis.Analyzer
){
    fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            val imageAnalysis = ImageAnalysis.Builder().setBackpressureStrategy(
                ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
            ).build()
            imageAnalysis.setAnalyzer(context.mainExecutor, analyzer)
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_FRONT_CAMERA, preview, imageAnalysis)


        }, context.mainExecutor)
    }
}