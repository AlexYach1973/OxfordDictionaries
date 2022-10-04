package com.alexyach.kotlin.translator.retrofit

import android.util.Log
import com.alexyach.kotlin.translator.retrofit.modelDto.WordTranslate
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val APP_ID = "fcff395e"
const val APP_KEY = "113c5a63ae64ab35fe6e17dce98f0a65"
const val BASE_URL = "https://od-api.oxforddictionaries.com/api/v2/"


class RetrofitImpl {

    private val serviceImpl = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        addCallAdapterFactory(CoroutineCallAdapterFactory())
    }.build()

    private val api: ITranslateService =
        serviceImpl.create(ITranslateService::class.java)


    fun getTranslateWord(word: String): WordTranslate? {

        var responseDto: WordTranslate? = null

        val job: Job = CoroutineScope(Dispatchers.IO).async {
            try {
                val result = api.translateServiceAsync(
                    APP_ID,
                    APP_KEY,
                    word
                ).await()

                withContext(Dispatchers.Main) {
                    responseDto = result
                }

            } catch (e: Exception) {
                Log.d("myLogs", "Error: ${e.message}")
            }
        }

        return responseDto

        /*
        val response = api.translateService(
            APP_ID,
            APP_KEY,
            word
        ).enqueue(object : Callback<WordTranslate> {
            override fun onResponse(call: Call<WordTranslate>, response: Response<WordTranslate>) {



                *//*val translateWord: List<Translation>? = response.body()?.results?.get(0)?.
                lexicalEntries?.get(0)?.entries?.get(0)?.senses?.get(0)?.translations

                Log.d("myLogs", "Code: ${response.code()}")
                Log.d("myLogs", "Size: ${translateWord?.size}")

                if (translateWord != null) {
                    for (it in translateWord) {
                        Log.d("myLogs", "Text: ${it.text} ")
                    }
                }*//*

                *//*val exampleWord: List<Translation>? = response.body()?.results?.get(0)?.
                lexicalEntries?.get(0)?.entries?.get(0)?.senses?.get(0)?.examples?.get(0)
                    ?.translations

                if (exampleWord != null) {
                    for (it in exampleWord) {
                        Log.d("myLogs", "Примеры: ${it.text} ")
                    }
                }*//*

            }

            override fun onFailure(call: Call<WordTranslate>, t: Throwable) {
                Log.d("myLogs", "Error: ${t.message}")
            }

        })
        */
    }

}