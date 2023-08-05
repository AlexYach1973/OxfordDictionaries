package com.alexyach.kotlin.translator.di

import androidx.room.Room
import com.alexyach.kotlin.translator.data.local.database.AppDatabase
import com.alexyach.kotlin.translator.data.local.database.WordsDao
import org.koin.dsl.module

val databaseModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    single<WordsDao> {
        get<AppDatabase>().getWordsDao()

    }


}
