package com.github.sunyawhite.notereader.Services

interface ITextRecognition {

    // method to recognize text on the image
    suspend fun RecognizeText(path : String) : String

}