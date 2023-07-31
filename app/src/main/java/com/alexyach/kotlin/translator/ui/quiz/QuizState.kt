package com.alexyach.kotlin.translator.ui.quiz

import com.alexyach.kotlin.translator.domain.model.QuizModel

sealed class QuizState {
    data class Success(val dataList: List<QuizModel>): QuizState()
    data class Error(val error: Exception): QuizState()
    object Loading: QuizState()
    object Started: QuizState()
}
