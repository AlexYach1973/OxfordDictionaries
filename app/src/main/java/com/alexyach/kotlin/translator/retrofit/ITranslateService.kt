package com.alexyach.kotlin.translator.retrofit

import com.alexyach.kotlin.translator.retrofit.modelDto.WordTranslate
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface ITranslateService {

    @GET("translations/en/ru/{word}")
    fun translateServiceAsync(
        @Header("app_id") app_id: String,
        @Header("app_key") app_key: String,
        @Path("word") word : String
    ): Deferred<WordTranslate>
//    ): Call<WordTranslate>

}