package com.alexyach.kotlin.translator.retrofit

import android.util.Log
import com.alexyach.kotlin.translator.ui.translate.TranslateWordState
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val APP_ID = "fcff395e"
const val APP_KEY = "113c5a63ae64ab35fe6e17dce98f0a65"
const val BASE_URL = "https://od-api.oxforddictionaries.com/api/v2/"


class RetrofitImpl : ITranslateRepository {

    private val serviceImpl = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
    }.build()

    private val api: ITranslateService =
        serviceImpl.create(ITranslateService::class.java)


    override suspend fun getTranslateWordAsync(word: String): TranslateWordState =
        withContext(Dispatchers.IO) {

            try {
                val response = api.translateServiceAsync(
                    APP_ID,
                    APP_KEY,
                    word
                )

                /*if (response.body() == null) {
                    return@withContext TranslateWordState.ErrorTranslateWord(Exception("Не має такого слова"))
                }*/

                when (response.code()) {
                    200 -> {
                        Log.d("myLogs", "RetrofitImpl, Thread: ${Thread.currentThread().name}")

                        return@withContext TranslateWordState.SuccessTranslateWord(response.body()!!)
                    }
                    404 -> return@withContext TranslateWordState.ErrorTranslateWord(Exception("Не знайдено"))
                    414 -> return@withContext TranslateWordState.ErrorTranslateWord(Exception("URI запит занадто довгий"))
                    500 -> return@withContext TranslateWordState.ErrorTranslateWord(Exception("Внутрішня помилка сервера"))
                    502 -> return@withContext TranslateWordState.ErrorTranslateWord(Exception("Поганий шлюз"))
                    503 -> return@withContext TranslateWordState.ErrorTranslateWord(Exception("Сервіс недоступний"))
                    504 -> return@withContext TranslateWordState.ErrorTranslateWord(Exception("Час очікування шлюзу"))
                }
            } catch (e: Exception) {
                TranslateWordState.ErrorTranslateWord(Exception(e))
            }

            return@withContext TranslateWordState.ErrorTranslateWord(Exception("Взагалі нічого не зрозуміло"))
        }

}