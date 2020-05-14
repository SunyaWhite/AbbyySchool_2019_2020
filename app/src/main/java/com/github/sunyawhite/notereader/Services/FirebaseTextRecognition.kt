package com.github.sunyawhite.notereader.Services

import android.content.Context
import android.net.Uri
import android.util.Log
import com.github.sunyawhite.notereader.R
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseTextRecognition(val context : Context) : ITextRecognition {


    override suspend fun RecognizeText(path: String): String  {
        return suspendCoroutine { continuation ->
            FirebaseApp.initializeApp(context)
            val image = FirebaseVisionImage.fromFilePath(context, Uri.parse(path))
            val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
            detector.processImage(image)
                .addOnSuccessListener { firebaseText ->
                    continuation.resume(firebaseText.text)
                }
                .addOnFailureListener { e ->
                    Log.e("FirebaseTextRecognition", e.message)
                    continuation.resume(R.string.Meow as String)
                }
        }
    }
}