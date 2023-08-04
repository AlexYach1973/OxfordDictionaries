package com.alexyach.kotlin.translator.di

import android.content.Context
import com.alexyach.kotlin.translator.data.local.database.AppDatabase
import com.alexyach.kotlin.translator.data.local.database.WordsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun wordsDao(database: AppDatabase): WordsDao =
        database.getWordsDao()
}