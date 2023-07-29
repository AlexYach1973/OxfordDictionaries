package com.alexyach.kotlin.translator.data.local

import android.util.Log
import com.alexyach.kotlin.translator.data.local.database.AppDatabase
import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel
import com.alexyach.kotlin.translator.domain.interfaces.IDatabaseRepository
import kotlinx.coroutines.flow.Flow

class DatabaseImpl(
    private val appDatabase: AppDatabase
) : IDatabaseRepository {
    override fun getAll(): Flow<List<WordsEntityModel>> {
        return appDatabase.getWordsDao().getAll()
    }

    override fun getTranslateByWord(word: String): Flow<WordsEntityModel> {
        return appDatabase.getWordsDao().getTranslateByWords(word)
    }

    override suspend fun insert(word: WordsEntityModel) {
        appDatabase.getWordsDao().insert(word)
    }

    override suspend fun delete(word: WordsEntityModel) {
        appDatabase.getWordsDao().delete(word)
    }

    override suspend fun deleteAll() {
        appDatabase.getWordsDao().deleteAll()
    }

}