package com.alexyach.kotlin.translator.ui.translate

import com.alexyach.kotlin.translator.domain.model.WordTranslateModel

sealed class TranslateWordState {
    data class Success(val wordModel: WordTranslateModel): TranslateWordState()
    data class Error(val error: Exception): TranslateWordState()

    object Loading: TranslateWordState()
    // Для початкового значення в StateFlow
    object Started: TranslateWordState()
}
