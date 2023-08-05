package com.alexyach.kotlin.translator.di

import com.alexyach.kotlin.translator.data.retrofit.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single<Gson> {
        GsonBuilder().setLenient().create()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    get<Gson>()
                )
            )
            .build()
    }

}
