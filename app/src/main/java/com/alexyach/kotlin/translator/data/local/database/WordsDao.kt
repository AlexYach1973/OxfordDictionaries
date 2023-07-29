package com.alexyach.kotlin.translator.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {
    @Query("SELECT * FROM wordsEntityModel")
    fun getAll(): Flow<List<WordsEntityModel>>

    @Query("SELECT * FROM wordsEntityModel WHERE wordInit=:word")
    fun getTranslateByWords(word: String): Flow<WordsEntityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordsEntityModel)

    @Delete
    suspend fun delete(word: WordsEntityModel)

    @Query("DELETE FROM wordsEntityModel")
    suspend fun deleteAll()

}