package com.example.apistest

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("choices")
    val choiceList: List<Choice>,

    @SerializedName("usage")
    val usage: Usage
)

data class Choice(
    @SerializedName("text")
    val text: String
)

data class Usage(
    @SerializedName("total_tokens")
    val totalToken: Int
)
