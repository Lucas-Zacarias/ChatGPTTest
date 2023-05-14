package com.example.apistest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    companion object{
        const val API_KEY = "no subir la api key a github"
    }

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer $API_KEY"
    )
    @POST("completions")
    fun createText(@Body apiRequest: ApiRequest): Call<ApiResponse>

}
