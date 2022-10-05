package com.alexyach.kotlin.translator.retrofit

import com.alexyach.kotlin.translator.ui.translate.TranslateWordState

interface ITranslateRepository {
    suspend fun getTranslateWordAsync(word: String): TranslateWordState
}