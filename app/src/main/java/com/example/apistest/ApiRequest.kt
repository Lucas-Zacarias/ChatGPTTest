package com.example.apistest

import com.google.gson.annotations.SerializedName

data class ApiRequest(
    @SerializedName("model")
    val model: String = "gpt-3.5-turbo",

    @SerializedName("messages")
    val messages: List<Message>
)

data class Message(
    val role: String,
    @SerializedName("content")
    val text: String
)
