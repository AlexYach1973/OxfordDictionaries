package com.alexyach.kotlin.translator.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordModel(
    val rusWord: String = "",
    val enWord: String = ""
): Parcelable
