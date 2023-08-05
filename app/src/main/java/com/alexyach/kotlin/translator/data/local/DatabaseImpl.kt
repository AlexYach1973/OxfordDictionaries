package com.alexyach.kotlin.translator.data.local

import com.alexyach.kotlin.translator.data.local.database.AppDatabase
import com.alexyach.kotlin.translator.data.local.database.WordsDao
import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel
import com.alexyach.kotlin.translator.domain.interfaces.IDatabaseRepository
import kotlinx.coroutines.flow.Flow

class DatabaseImpl(
    private val wordsDao: WordsDao
) : IDatabaseRepository {
    override fun getAll(): Flow<List<WordsEntityModel>> {
        return wordsDao.getAll()
    }

    override fun getTranslateByWord(word: String): Flow<WordsEntityModel> {
        return wordsDao.getTranslateByWords(word)
    }

    override suspend fun insert(word: WordsEntityModel) {
        wordsDao.insert(word)
    }

    override suspend fun delete(word: WordsEntityModel) {
        wordsDao.delete(word)
    }

    override suspend fun deleteAll() {
        wordsDao.deleteAll()
    }

}