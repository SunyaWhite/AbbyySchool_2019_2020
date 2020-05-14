package com.github.sunyawhite.notereader.Services

import android.content.Context
import android.net.Uri
import android.util.Log
import com.github.sunyawhite.notereader.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class FirebaseTextRecognition(val context : Context) : ITextRecognition {


    override suspend fun RecognizeText(path: String): String = withContext(Dispatchers.Default){
        try {
            var resultText : String = "Some default text"
            val image = FirebaseVisionImage.fromFilePath(context, Uri.parse(path))
            val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
            val result = detector.processImage(image)
                .addOnSuccessListener { firebaseText ->
                    resultText = firebaseText.text
                }
                .addOnFailureListener { e ->
                    throw e
                }
            resultText
        }
        catch (exc : Exception){
            Log.e("FirebaseTextRecognition", exc.message)
            R.string.Meow as String
        }
    }
}