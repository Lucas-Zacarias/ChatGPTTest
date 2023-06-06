package com.example.apistest

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var textInput: EditText
    private lateinit var btnGetText: Button
    private lateinit var txtTextGenerated: TextView
    /*private val clientOkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val request =
                chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer ${ApiInterface.API_KEY}")
                    .build()
            chain.proceed(request)
        }
        .build()*/

    private val serviceGetTextFromPrompt: ApiInterface = Retrofit.Builder()
        .baseUrl("https://api.openai.com/v1/")
        .client(OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    private val serviceGetTextFromAudio: ApiInterface = Retrofit.Builder()
        .baseUrl("https://api.openai.com/v1/audio/")
        .client(OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .build())
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
/*            val response =
                getText("Necesito un texto de 2 párrafos para ser leído por niños que incluya las siguientes palabras: enseñanza, aprendizaje, casamiento, entretenimiento")
            response.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if(response.isSuccessful &&
                        response.body() != null){
                        txtTextGenerated.text = response.body()!!.choiceList[0].text
                    }else{
                        Log.i("Lucas", response.toString())
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_LONG).show()

                }

            })*/

            val audioFile = getAudioFileFromRaw(R.raw.record_test, "test.m4a")

            val responseAudio =
                getTextFromAudio(audioFile)

            responseAudio.enqueue(object: Callback<TranscribeResponse>{
                override fun onResponse(
                    call: Call<TranscribeResponse>,
                    response: Response<TranscribeResponse>
                ) {
                    if(response.isSuccessful && response.body() != null){
                        Log.i("Prueba", response.body()!!.text)
                    }
                }

                override fun onFailure(call: Call<TranscribeResponse>, t: Throwable) {
                    Log.i("Fallo", t.message.toString())
                }

            })

        }
    }
    private fun getText(inputText: String): Call<ApiResponse> {
        val apiRequest = ApiRequest(prompt = inputText)
        return serviceGetTextFromPrompt.createText(apiRequest)
    }

    private fun getTextFromAudio(audioFile: File): Call<TranscribeResponse>{
        val requestBody = RequestBody.create(MediaType.parse("audio/mp3"), audioFile)
        val filePart = MultipartBody.Part.createFormData("file", audioFile.name, requestBody)
        return serviceGetTextFromAudio.transcribe(file=filePart, model="whisper-1", language = "es")

    }

    private fun getAudioFileFromRaw(resourceId: Int, fileName: String): File {
        val file = File(filesDir, fileName)
        if (!file.exists()) {
            val inputStream: InputStream = resources.openRawResource(resourceId)
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }

            inputStream.close()
            outputStream.flush()
            outputStream.close()
        }

        return file
    }
}
