package com.alexyach.kotlin.translator.di.modules

import android.content.Context
import androidx.room.Room
import com.alexyach.kotlin.translator.data.local.database.AppDatabase
import com.alexyach.kotlin.translator.data.local.database.WordsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun database(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()

    @Singleton
    @Provides
    fun wordsDao(database: AppDatabase): WordsDao =
        database.getWordsDao()

}
