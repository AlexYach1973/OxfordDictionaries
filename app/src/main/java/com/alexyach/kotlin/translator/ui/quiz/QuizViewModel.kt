package com.alexyach.kotlin.translator.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.translator.data.local.DatabaseImpl
import com.alexyach.kotlin.translator.data.local.database.AppDatabase
import com.alexyach.kotlin.translator.domain.interfaces.IDatabaseRepository
import com.alexyach.kotlin.translator.domain.model.QuizModel
import com.alexyach.kotlin.translator.ui.base.UIState
import com.alexyach.kotlin.translator.utils.entityListToQuizList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class QuizViewModel(database: AppDatabase) : ViewModel() {

    private val roomRepository: IDatabaseRepository = DatabaseImpl(database)

    private var _listWordsStateFlow : MutableStateFlow<UIState<List<QuizModel>>>
            = MutableStateFlow(UIState.Started)
    val listWordsStateFlow: StateFlow<UIState<List<QuizModel>>> = _listWordsStateFlow

    private var _initWordFlow = MutableStateFlow(QuizModel(0, "", "", true))
    var initWordFlow : StateFlow<QuizModel> = _initWordFlow

    private var quizListWords = mutableListOf<QuizModel>()

    init {
        getListWords()
    }


    fun guessWord(guessList: List<QuizModel>, word: QuizModel) {
        if (word.id == _initWordFlow.value.id) {
            toTrueIsGuess()
            selectionWords()
        } else {
            guessList.filter { it.id == word.id }.forEach { it.isGuess = false }
        }
    }

    private fun toTrueIsGuess() {
        quizListWords.forEach { it.isGuess = true }
    }

    private fun getListWords() {
        _listWordsStateFlow.value = UIState.Loading

        viewModelScope.launch {
            roomRepository.getAll()
                .flowOn(Dispatchers.IO)
                .collect {
                    quizListWords = entityListToQuizList(it)
                    selectionWords()
                }
        }
    }

    private fun selectionWords() {
        quizListWords.shuffle()

        if (quizListWords.size < 8) {
            randomWords(quizListWords)
        } else {
            val newDataList: MutableList<QuizModel> = mutableListOf()
            for (i in 0..7) {
                newDataList.add(quizListWords[i])
            }
            randomWords(newDataList)
        }
    }

    private fun randomWords(dataList: MutableList<QuizModel>) {
        _initWordFlow.value = dataList[0]
        dataList.shuffle()
        _listWordsStateFlow.value =  UIState.Success(dataList)
    }


    companion object {
        fun getViewModelFactory(database: AppDatabase): ViewModelProvider.Factory {
            val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return QuizViewModel(database) as T
                }
            }
            return factory
        }
    }
}
