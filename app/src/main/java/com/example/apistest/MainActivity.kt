package com.example.apistest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

class MainActivity : AppCompatActivity() {
    private lateinit var textInput: EditText
    private lateinit var btnGetText: Button
    private lateinit var txtTextGenerated: TextView
    private val clientOkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val request =
                chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer ${ApiInterface.API_KEY}")
                    .build()
            chain.proceed(request)
        }
        .build()

    private val serviceGetTextFromPrompt: ApiInterface = Retrofit.Builder()
        .baseUrl("https://api.openai.com/v1/")
        .client(clientOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInput = findViewById<EditText>(R.id.etPrompt)
        btnGetText = findViewById<Button>(R.id.btnGetText)
        txtTextGenerated = findViewById<TextView>(R.id.tvApiResponse)

        btnGetText.setOnClickListener {
            val response =
                getText("Necesito un texto de 3 párrafos para ser leído por niños que incluya las siguientes palabras: enseñanza, aprendizaje, casamiento, entretenimiento")
            response.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if(response.isSuccessful &&
                        response.body() != null){
                        txtTextGenerated.text = response.body()!!.choiceList[0].text
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_LONG).show()
                }

            })

        }
    }
    private fun getText(inputText: String): Call<ApiResponse> {
        val apiRequest = ApiRequest(model= "text-davinci-003", textRequested = inputText, maxTokens = 4000)
        return serviceGetTextFromPrompt.createText(apiRequest)
    }
}
