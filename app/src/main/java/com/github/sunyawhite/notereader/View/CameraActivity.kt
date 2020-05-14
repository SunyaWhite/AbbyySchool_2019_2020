package com.github.sunyawhite.notereader.View

import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureConfig
import androidx.camera.view.CameraView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.Note
import com.github.sunyawhite.notereader.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity(), ImageCapture.OnImageSavedListener {

    // Unique code for permissions
    private val PERMISSION_REQUEST_CODE = 12
    // Required permissions
    private val REQUIRED_PERMISSIONS = arrayOf(CAMERA, READ_EXTERNAL_STORAGE)

    private lateinit var cameraExecutor : ExecutorService

    private var currentPhotoPath : String = ""

    private lateinit var imageView : CameraView

    private lateinit var cameraButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        imageView = findViewById<CameraView>(R.id.cameraView)
        cameraButton = findViewById<Button>(R.id.cameraButton)
        cameraExecutor = Executors.newSingleThreadExecutor()

        if(checkPermissions())
            startCamera()
        else
            requestPermissions()
    }

    private fun startCamera(){

        //CameraX.unbindAll()
        imageView.captureMode = CameraView.CaptureMode.IMAGE
        imageView.bindToLifecycle(this)

        cameraButton.setOnClickListener { v ->
                imageView.takePicture(generateFile(), cameraExecutor, this)
        }

    }

    private fun generateFile() : File = File(externalMediaDirs.first().absolutePath, "${System.currentTimeMillis().toString()}.jpg")

    private fun checkPermissions() = REQUIRED_PERMISSIONS.all { permission ->
        ActivityCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED
    }

    private fun requestPermissions(){
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && checkPermissions())
                    startCamera()
                else{
                    Toast.makeText(this,"Permission are not granted", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
            }
        }
    }

    override fun onImageSaved(file: File) {
        runBlocking {
            // service locator
            val repository : INoteRepository = get()
            Log.d("CameraActivity", "Image taken. Path : ${file.path}")
            repository.addNewNote(Note(repository.getNewId(), Date(System.currentTimeMillis()), "TestText1", file.path))
            withContext(Dispatchers.Main){
                super.onBackPressed()
            }
            delay(100)
        }
    }

    override fun onError(
        imageCaptureError: ImageCapture.ImageCaptureError,
        message: String,
        cause: Throwable?
    ) {
        Log.e("CameraActivity", "Error occurred while taking images")
    }

    override fun onDestroy() {
        cameraExecutor.shutdown()
        super.onDestroy()
    }

}
