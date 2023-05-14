package com.example.apistest

import com.google.gson.annotations.SerializedName

data class ApiRequest(
    @SerializedName("model")
    val model: String,

    @SerializedName("prompt")
    val textRequested: String,

    @SerializedName("max_tokens")
    val maxTokens: Int
)
