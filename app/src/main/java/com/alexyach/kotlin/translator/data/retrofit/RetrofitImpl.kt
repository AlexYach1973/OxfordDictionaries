package com.alexyach.kotlin.translator.data.retrofit

import com.alexyach.kotlin.translator.data.retrofit.modelDto.WordTranslate
import com.alexyach.kotlin.translator.domain.interfaces.IRemoteRepository
import com.alexyach.kotlin.translator.domain.model.Language
import com.alexyach.kotlin.translator.domain.model.WordTranslateModel
import com.alexyach.kotlin.translator.ui.base.UIState
import com.alexyach.kotlin.translator.utils.APP_ID
import com.alexyach.kotlin.translator.utils.APP_KEY
import com.alexyach.kotlin.translator.utils.wordDtoToWordTranslateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitImpl @Inject constructor(
    val retrofit: Retrofit
) : IRemoteRepository {

    private val api = retrofit.create(ITranslateService::class.java)


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
                            api.translateServiceAsyncRuEn(
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
