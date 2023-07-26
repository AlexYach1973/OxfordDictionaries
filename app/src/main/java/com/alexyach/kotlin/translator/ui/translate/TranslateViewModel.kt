package com.alexyach.kotlin.translator.ui.translate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.translator.data.retrofit.ITranslateRepository
import com.alexyach.kotlin.translator.data.retrofit.RetrofitImpl
import com.alexyach.kotlin.translator.data.retrofit.modelDto.WordTranslate
import com.alexyach.kotlin.translator.domain.model.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TranslateViewModel : ViewModel() {

    private val repository: ITranslateRepository = RetrofitImpl()


    /** StateFlow */
    private val _translateWordStateFlow: MutableStateFlow<TranslateWordState> =
        MutableStateFlow(TranslateWordState.Started)
    val translateWordStateFlow: StateFlow<TranslateWordState> = _translateWordStateFlow

    private val _soundPathStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val soundPathStateFlow: StateFlow<String?> = _soundPathStateFlow

    fun getTranslateWordFlow(word: String, language: Language) = viewModelScope.launch {
        _translateWordStateFlow.value = TranslateWordState.Loading

        repository.getTranslateWordAsync(word, language)
            .catch { Log.d("myLogs", "TranslateViewModel: error") }
            .collect {
                _translateWordStateFlow.value = it
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



