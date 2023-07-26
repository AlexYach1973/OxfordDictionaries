package com.alexyach.kotlin.translator.data.retrofit

import com.alexyach.kotlin.translator.data.retrofit.modelDto.WordTranslate
import com.alexyach.kotlin.translator.domain.model.Language
import com.alexyach.kotlin.translator.ui.translate.TranslateWordState
import com.alexyach.kotlin.translator.utils.wordDtoToWordTranslateModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
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


    override fun getTranslateWordAsync(word: String, language: Language) =

        when (language) {
            Language.En -> {
                flow {
                    try {
                        val response =
                            api.translateServiceAsyncEnRu(
                                APP_ID,
                                APP_KEY, word)

                        if (response.body() == null) {
                            emit(TranslateWordState.Error(Exception("Не має такого слова")))
                        } else {
                            emit(responseByCode(response))
                        }

                    } catch (e: Exception) {
                        TranslateWordState.Error(Exception(e))
                    }
// Log.d("myLogs", "RetrofitImpl Thread: ${Thread.currentThread().name}") worker-1
                }.flowOn(Dispatchers.IO)
            }

            Language.Ru -> {
                flow {
                    try {
                        val response =
                            api.translateServiceAsyncRuEn(
                                APP_ID,
                                APP_KEY, word)

                        if (response.body() == null) {
                            emit(TranslateWordState.Error(Exception("Не має такого слова")))
                        } else {
                            emit(responseByCode(response))
                        }

                    } catch (e: Exception) {
                        TranslateWordState.Error(Exception(e))
                    }
                }.flowOn(Dispatchers.IO)
            }
        }

    private fun responseByCode(response: Response<WordTranslate>): TranslateWordState {
        when (response.code()) {
            200 -> {
                return TranslateWordState.Success(wordDtoToWordTranslateModel(response.body()!!))
            }
            404 -> return TranslateWordState.Error(Exception("Не знайдено"))
            414 -> return TranslateWordState.Error(Exception("URI запит занадто довгий"))
            500 -> return TranslateWordState.Error(Exception("Внутрішня помилка сервера"))
            502 -> return TranslateWordState.Error(Exception("Поганий шлюз"))
            503 -> return TranslateWordState.Error(Exception("Сервіс недоступний"))
            504 -> return TranslateWordState.Error(Exception("Час очікування шлюзу"))
        }
        return TranslateWordState.Error(Exception("Не зрозуміла похибка"))
    }


}