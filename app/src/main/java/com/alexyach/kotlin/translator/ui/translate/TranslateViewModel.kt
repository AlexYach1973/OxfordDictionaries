package com.alexyach.kotlin.translator.ui.translate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel
import com.alexyach.kotlin.translator.data.retrofit.modelDto.WordTranslate
import com.alexyach.kotlin.translator.domain.interfaces.IDatabaseRepository
import com.alexyach.kotlin.translator.domain.interfaces.IRemoteRepository
import com.alexyach.kotlin.translator.domain.model.Language
import com.alexyach.kotlin.translator.domain.model.WordTranslateModel
import com.alexyach.kotlin.translator.ui.base.UIState
import com.alexyach.kotlin.translator.utils.NO_TRANSLATE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class TranslateViewModel @Inject constructor(
    private val roomRepository: IDatabaseRepository,
    private val remoteRepository: IRemoteRepository
) : ViewModel() {

    /** StateFlow */
    private val _translateWordStateFlow: MutableStateFlow<UIState<WordTranslateModel>> =
        MutableStateFlow(UIState.Started)
    val translateWordStateFlow: StateFlow<UIState<WordTranslateModel>> = _translateWordStateFlow

    private val _soundPathStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val soundPathStateFlow: StateFlow<String?> = _soundPathStateFlow

    private var listWordsRoom: List<WordsEntityModel> = listOf()

    // For Toast
    private var _toastMessage: MutableStateFlow<Int> = MutableStateFlow(0)
    val toastMessage: StateFlow<Int> = _toastMessage

    init {
        getListWordsFromRoom()
    }
    fun getTranslateWordFlow(word: String, language: Language) = viewModelScope.launch {
        _translateWordStateFlow.value = UIState.Loading

        remoteRepository.getTranslateWordAsync(word, language)
            .catch { Log.d("myLogs", "TranslateViewModel: error") }
            .collect {
                _translateWordStateFlow.value = it
            }
    }

    fun insertWordToDatabase(
        wordInit: String,
        translateWord: WordTranslateModel) {

        if (isContainsWordInRoom(wordInit)) {
            _toastMessage.value = 1
            return
        }

        viewModelScope.launch {
            roomRepository.insert(
                WordsEntityModel(
                    0,
                    wordInit = wordInit,
                    wordTranslate = collectTranslateWords(translateWord)
//                    wordTranslate = translateWord.translateWordList[0]
                )
            )
        }
    }

    private fun collectTranslateWords(translateWord: WordTranslateModel): String {
        var collectWord = ""
        translateWord.translateWordList.forEach {
            if (it != NO_TRANSLATE) {
                collectWord += "$it\n"
            }
        }
        return collectWord
    }

    private fun isContainsWordInRoom(word: String): Boolean {
        var isContains = false
        listWordsRoom.forEach{
            if (it.wordInit == word) {
                isContains = true
            }
        }
        return isContains
    }


    private fun getListWordsFromRoom() {
        viewModelScope.launch {
            roomRepository.getAll()
                .flowOn(Dispatchers.IO)
                .collect { listWordsRoom = it }
        }
    }

    private fun soundPath(wordDto: WordTranslate) {
        val soundPath: String? = wordDto?.results?.get(0)?.lexicalEntries?.get(0)
            ?.entries?.get(0)?.pronunciations?.get(0)?.audioFile

        if (!soundPath.isNullOrEmpty()) {
            _soundPathStateFlow.value = soundPath
        }
    }
}



