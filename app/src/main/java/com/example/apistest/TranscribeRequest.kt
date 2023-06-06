package com.example.apistest

import com.google.gson.annotations.SerializedName

data class TranscribeRequest(
    @SerializedName("model")
    val model: String = "whisper-1",

    @SerializedName("file")
    val audioFile: String,

    @SerializedName("language")
    val language: String = "es"
)
