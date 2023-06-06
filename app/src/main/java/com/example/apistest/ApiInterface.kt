package com.example.apistest

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    companion object{
        const val API_KEY = "no subir api key"
    }

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer $API_KEY"
    )
    @POST("completions")
    fun createText(@Body apiRequest: ApiRequest): Call<ApiResponse>

    @Headers(
        "Authorization: Bearer $API_KEY"
    )
    @Multipart
    @POST("transcriptions")
    fun transcribe(
        @Part file: MultipartBody.Part,
        @Part("model") model: RequestBody
    ): Call<TranscribeResponse>
}
