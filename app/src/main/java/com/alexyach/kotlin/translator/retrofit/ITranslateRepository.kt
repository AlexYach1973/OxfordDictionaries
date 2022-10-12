package com.alexyach.kotlin.translator.retrofit

import com.alexyach.kotlin.translator.ui.translate.TranslateWordState
import kotlinx.coroutines.flow.Flow

interface ITranslateRepository {
    suspend fun getTranslateWordAsync(word: String): Flow<TranslateWordState>
}