package com.alexyach.kotlin.translator.domain.model

//@Parcelize
data class WordTranslateModel(
    val translateWordList: List<String> = emptyList(),
    val exampleWordList: List<String> = emptyList(),
    val soundPath: String = ""
)
//    : Parcelable
