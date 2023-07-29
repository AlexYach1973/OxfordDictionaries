package com.alexyach.kotlin.translator.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordsEntityModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val wordInit: String,
    val wordTranslate: String
)