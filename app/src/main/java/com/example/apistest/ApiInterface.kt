package com.example.apistest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    companion object{
        const val API_KEY = "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"
    }

    @POST("completions")
    fun createText(@Body apiRequest: ApiRequest): Call<ApiResponse>

}
