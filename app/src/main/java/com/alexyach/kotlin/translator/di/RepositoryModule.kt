package com.alexyach.kotlin.translator.di

import com.alexyach.kotlin.translator.data.local.DatabaseImpl
import com.alexyach.kotlin.translator.data.local.database.WordsDao
import com.alexyach.kotlin.translator.data.retrofit.RetrofitImpl
import com.alexyach.kotlin.translator.domain.interfaces.IDatabaseRepository
import com.alexyach.kotlin.translator.domain.interfaces.IRemoteRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<IDatabaseRepository> {
        DatabaseImpl(wordsDao = get<WordsDao>())
    }

    single<IRemoteRepository> {
        RetrofitImpl(retrofit = get())
    }
}
