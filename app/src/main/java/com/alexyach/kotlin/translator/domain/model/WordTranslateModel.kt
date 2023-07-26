package com.alexyach.kotlin.translator.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordTranslateModel(
    val translateWordList: List<String> = emptyList(),
    val exampleWordList: List<String> = emptyList(),
    val soundPath: String = ""
): Parcelable
