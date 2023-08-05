package com.alexyach.kotlin.translator.ui.listwords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel
import com.alexyach.kotlin.translator.domain.interfaces.IDatabaseRepository
import com.alexyach.kotlin.translator.ui.base.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

const val SEARCH_SYMBOL = "♥"
class ListWordsViewModel(
    val roomRepository: IDatabaseRepository
) : ViewModel() {

    private var _listWordsStateFlow : MutableStateFlow<UIState<List<WordsEntityModel>>>
            = MutableStateFlow(UIState.Started)
    val listWordsStateFlow: StateFlow<UIState<List<WordsEntityModel>>> = _listWordsStateFlow

    private var listWord = mutableListOf<WordsEntityModel>()


    init {
        getListWords()
    }

    private fun getListWords() {
        _listWordsStateFlow.value = UIState.Loading

        viewModelScope.launch {
            roomRepository.getAll()
                .flowOn(Dispatchers.IO)
                .collect {
                    listWord = it.toMutableList()
                    _listWordsStateFlow.value =  UIState.Success(it)
                }
        }
    }

    fun deleteWord(word: WordsEntityModel) {
        viewModelScope.launch {
            roomRepository.delete(word)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            roomRepository.deleteAll()
        }
    }

    fun searchWord(symbols: String) {
        val searchListWord = mutableListOf<WordsEntityModel>()

        listWord.forEach {
            if (it.wordInit.contains(symbols) || it.wordTranslate.contains(symbols)) {

                searchListWord.add( it.copy(
                    wordInit = replaceSymbols(it.wordInit, symbols),
                    wordTranslate = replaceSymbols(it.wordTranslate, symbols)
                ))
            }
        }
        _listWordsStateFlow.value =  UIState.Success(searchListWord)
    }
    private fun replaceSymbols(str: String, symbols: String): String {
        return str.replace(symbols, "$SEARCH_SYMBOL$symbols")
    }
    fun removeSearchSymbol() {
        listWord.forEach {
            it.wordInit.replace(SEARCH_SYMBOL,"")
            it.wordTranslate.replace("*","")
        }
        _listWordsStateFlow.value =  UIState.Success(listWord)
    }
}

