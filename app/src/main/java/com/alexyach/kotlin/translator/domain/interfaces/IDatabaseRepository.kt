package com.alexyach.kotlin.translator.domain.interfaces

import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel
import com.alexyach.kotlin.translator.domain.model.WordsDatabaseModel
import kotlinx.coroutines.flow.Flow

interface IDatabaseRepository {
    fun getAll() : Flow<List<WordsEntityModel>>
    fun getTranslateByWord(word: String): Flow<WordsEntityModel>
    suspend fun insert(word: WordsEntityModel)
    suspend fun delete(word: WordsEntityModel)
    suspend fun deleteAll()
}