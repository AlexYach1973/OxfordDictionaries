package com.alexyach.kotlin.translator.ui.listwords

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.translator.data.local.DatabaseImpl
import com.alexyach.kotlin.translator.data.local.database.AppDatabase
import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel
import com.alexyach.kotlin.translator.domain.interfaces.IDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ListWordsViewModel(database: AppDatabase) : ViewModel() {

    private val roomRepository: IDatabaseRepository = DatabaseImpl(database)

    private var _listWordsStateFlow : MutableStateFlow<ListWordsState>
            = MutableStateFlow(ListWordsState.Started)
    val listWordsStateFlow: StateFlow<ListWordsState> = _listWordsStateFlow

    private var listWord = mutableListOf<WordsEntityModel>()


    init {
        getListWords()
    }

    private fun getListWords() {
        _listWordsStateFlow.value = ListWordsState.Loading

        viewModelScope.launch {
            roomRepository.getAll()
                .flowOn(Dispatchers.IO)
                .collect {
                    listWord = it.toMutableList()
                    _listWordsStateFlow.value =  ListWordsState.Success(it)
                }
        }
    }

    fun deleteWord(word: WordsEntityModel) {
        viewModelScope.launch {
            roomRepository.delete(word)
        }
    }

    fun searchWord(symbols: String) {
        val searchListWord = mutableListOf<WordsEntityModel>()

        listWord.forEach {
            if (it.wordInit.contains(symbols) || it.wordTranslate.contains(symbols)) {
                searchListWord.add(it)
            }
        }
        _listWordsStateFlow.value =  ListWordsState.Success(searchListWord)
    }

    companion object {
        fun getViewModelFactory(database: AppDatabase): ViewModelProvider.Factory {
            val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ListWordsViewModel(database) as T
                }
            }
            return factory
        }
    }
}

