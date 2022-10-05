package com.alexyach.kotlin.translator.ui.translate

import com.alexyach.kotlin.translator.retrofit.modelDto.WordTranslate
import java.lang.Exception

sealed class TranslateWordState {
    data class SuccessTranslateWord(val word: WordTranslate): TranslateWordState()
    data class ErrorTranslateWord(val error: Exception): TranslateWordState()

    object Loading: TranslateWordState()
}
