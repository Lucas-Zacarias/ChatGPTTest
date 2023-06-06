package com.example.apistest

import com.google.gson.annotations.SerializedName

data class TranscribeResponse(
    @SerializedName("text")
    val text: String
)
