package com.alexyach.kotlin.translator.data.retrofit

import com.alexyach.kotlin.translator.data.retrofit.modelDto.WordTranslate
import com.alexyach.kotlin.translator.domain.interfaces.ITranslateRepository
import com.alexyach.kotlin.translator.domain.model.Language
import com.alexyach.kotlin.translator.domain.model.WordTranslateModel
import com.alexyach.kotlin.translator.ui.base.UIState
import com.alexyach.kotlin.translator.utils.wordDtoToWordTranslateModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val APP_ID = ""
const val APP_KEY = ""
const val BASE_URL = "https://od-api.oxforddictionaries.com/api/v2/"


class RetrofitImpl : ITranslateRepository {



    override fun getTranslateWordAsync(word: String, language: Language) =

        when (language) {
            Language.En -> {
                flow {
                    try {
                        val response =
                            RetrofitBuilder.api.translateServiceAsyncEnRu(
                                APP_ID,
                                APP_KEY, word)

                        if (response.body() == null) {
                            emit(UIState.Error("Немає такого слова"))
                        } else {
                            emit(responseByCode(response))
                        }

                    } catch (e: Exception) {
                        UIState.Error(e.message ?: "???")
                    }
// Log.d("myLogs", "RetrofitImpl Thread: ${Thread.currentThread().name}") worker-1
                }.flowOn(Dispatchers.IO)
            }

            Language.Ru -> {
                flow {
                    try {
                        val response =
                            RetrofitBuilder.api.translateServiceAsyncRuEn(
                                APP_ID,
                                APP_KEY, word)

                        if (response.body() == null) {
                            emit(UIState.Error("Немає такого слова"))
                        } else {
                            emit(responseByCode(response))
                        }

                    } catch (e: Exception) {
                        UIState.Error(e.message ?: "???")
                    }
                }.flowOn(Dispatchers.IO)
            }
        }

    private fun responseByCode(response: Response<WordTranslate>): UIState<WordTranslateModel> {
        when (response.code()) {
            200 -> {
                return UIState.Success(wordDtoToWordTranslateModel(response.body()!!))
            }
            404 -> return UIState.Error("Не знайдено")
            414 -> return UIState.Error("URI запит занадто довгий")
            500 -> return UIState.Error("Внутрішня помилка сервера")
            502 -> return UIState.Error("Поганий шлюз")
            503 -> return UIState.Error("Сервіс недоступний")
            504 -> return UIState.Error("Час очікування шлюзу")
        }
        return UIState.Error("Не зрозуміла похибка")
    }
}

object RetrofitBuilder {
    private val serviceImpl = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
    }.build()

    val api: ITranslateService =
        serviceImpl.create(ITranslateService::class.java)

}
