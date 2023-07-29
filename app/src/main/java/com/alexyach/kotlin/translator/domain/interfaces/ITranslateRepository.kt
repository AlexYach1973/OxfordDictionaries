package com.alexyach.kotlin.translator.domain.interfaces

import com.alexyach.kotlin.translator.domain.model.Language
import com.alexyach.kotlin.translator.ui.translate.TranslateWordState
import kotlinx.coroutines.flow.Flow

interface ITranslateRepository {
    fun getTranslateWordAsync(word: String, language: Language): Flow<TranslateWordState>
}