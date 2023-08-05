package com.alexyach.kotlin.translator.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordsEntityModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getWordsDao(): WordsDao
}