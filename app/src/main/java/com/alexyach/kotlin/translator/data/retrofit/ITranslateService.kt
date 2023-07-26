package com.alexyach.kotlin.translator.data.retrofit

import com.alexyach.kotlin.translator.data.retrofit.modelDto.WordTranslate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface ITranslateService {

    @GET("translations/en/ru/{word}")
    suspend fun translateServiceAsyncEnRu(
        @Header("app_id") app_id: String,
        @Header("app_key") app_key: String,
        @Path("word") word : String
    ): Response<WordTranslate>

    @GET("translations/ru/en/{word}")
    suspend fun translateServiceAsyncRuEn(
        @Header("app_id") app_id: String,
        @Header("app_key") app_key: String,
        @Path("word") word : String
    ): Response<WordTranslate>


}