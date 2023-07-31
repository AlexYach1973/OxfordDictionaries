package com.alexyach.kotlin.translator.domain.model

data class QuizModel(
    val id: Int,
    val wordInit: String,
    val wordTranslate: String,
    var isGuess: Boolean = true
)
