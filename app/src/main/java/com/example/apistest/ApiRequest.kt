package com.example.apistest

import com.google.gson.annotations.SerializedName

data class ApiRequest(
    val model: String = "text-davinci-003",
    val prompt: String,
    val max_tokens: Int = 1000
    /*@SerializedName("model")
    val model: String = "gpt-3.5-turbo",

    @SerializedName("messages")
    val messages: List<Message>*/
)

data class Message(
    @SerializedName("role")
    val role: String,

    @SerializedName("content")
    val text: String
)
