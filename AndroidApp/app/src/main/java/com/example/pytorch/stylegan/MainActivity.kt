package com.example.pytorch.stylegan

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.util.Log
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar

import kotlin.random.Random
import kotlinx.coroutines.*

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.Tensor
import org.pytorch.LiteModuleLoader


class MainActivity() : AppCompatActivity() {

    lateinit var mProgressBar: ProgressBar
    lateinit var buttonRestart: Button
    lateinit var module: Module

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var worker: Thread? = null

        fun assetFilePath(context: Context, assetName: String): String? {
            val file = File(context.filesDir, assetName)

            try {
                context.assets.open(assetName).use { `is` ->
                    FileOutputStream(file).use { os ->
                        val buffer = ByteArray(4 * 1024)
                        while (true) {
                            val length = `is`.read(buffer)
                            if (length <= 0)
                                break
                            os.write(buffer, 0, length)
                        }
                        os.flush()
                        os.close()
                    }
                    return file.absolutePath
                }
            } catch (e: IOException) {
                Log.e("pytorchandroid", "Error process asset $assetName to file path")
            }

            return null
        }// end asseFilePath

        // "module get"
        val module_path = assetFilePath(this, "stylegan_ffhq1024_input_z.ptl")
        Log.d("TAG", "module path = <$module_path>")

        try {
            module = LiteModuleLoader.load(module_path)
            Log.d("TAG", "module loading successfully")
        } catch (e: IOException) {
            Log.e("ASLRecognition", "Error reading model file", e)
            finish()
        } // end of "module get"


        // "preparing input tensor"
        val inputBuffer = Tensor.allocateFloatBuffer(
            1*512
        )
        val inputTensor = Tensor.fromBlob(
            inputBuffer,
            listOf(1, 512)
                .map { it.toLong() }
                .toLongArray()
        )
        for (i in 0..511) {
            inputBuffer.put(i, Random.nextFloat() * 2f -1f)
            Log.d("outputArray", "outputArray[$i] = ${Random.nextFloat() * 2f -1f}")
        }
        // end of "preparing input tensor"

        // "running the model"
        var mImageView: ImageView = findViewById(R.id.imageView)
        buttonRestart = findViewById(R.id.restartButton)
        mProgressBar = findViewById(R.id.progressBar)

        buttonRestart.setOnClickListener {
            buttonRestart.setEnabled(false)
            mProgressBar.setVisibility(ProgressBar.VISIBLE)
            try {
                Log.d("TAG", "module start to cumpute ")
                val output = module.forward(IValue.from(inputTensor))

                // "getting tensor content as kotlin array of int32"
                val outputTensor = output.toTensor()
                val outputArray = outputTensor.dataAsIntArray
                // end of "getting tensor content as kotlin array of int32"
                mImageView.setImageBitmap(
                    Bitmap.createBitmap(
                        outputArray, 1024, 1024, Bitmap.Config.ARGB_8888
                    )
                )
                Log.d("TAG", "module cumpute successfully")

            } catch (e: IOException) {
                Log.e("ASLRecognition", "Error happened during computing ", e)
                finish()
            }


            buttonRestart.setEnabled(true)
            mProgressBar.setVisibility(ProgressBar.INVISIBLE)
        }//buttonRestart.setOnClickListener


    }//onCreate

}
