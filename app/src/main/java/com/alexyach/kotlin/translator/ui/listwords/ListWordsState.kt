package com.alexyach.kotlin.translator.ui.listwords

import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel

sealed class ListWordsState {
    data class Success(val dataList: List<WordsEntityModel>): ListWordsState()
    data class Error(val error: Exception): ListWordsState()
    object Loading: ListWordsState()
    object Started: ListWordsState()
}