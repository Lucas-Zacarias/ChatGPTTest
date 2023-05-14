package com.example.apistest

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("model")
    val model: String,

    @SerializedName("choices")
    val choiceList: List<Choice>,

    @SerializedName("usage")
    val usage: Usage
)

data class Choice(
    @SerializedName("name")
    val text: String,

    @SerializedName("index")
    val index: Int
)

data class Usage(
    @SerializedName("total_tokens")
    val totalToken: Int
)
