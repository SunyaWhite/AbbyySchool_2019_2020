package com.github.sunyawhite.notereader.Services

/*
    Интерфейс для для системы распознавания текста
 */
interface ITextRecognition {

    // method to recognize text on the image
    suspend fun RecognizeText(path : String) : String

}