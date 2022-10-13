package com.alexyach.kotlin.translator.ui.translate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.translator.retrofit.ITranslateRepository
import com.alexyach.kotlin.translator.retrofit.RetrofitImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TranslateViewModel : ViewModel() {

    private val repository: ITranslateRepository = RetrofitImpl()

    /** StateFlow */
    private val _translateWordStateFlow: MutableStateFlow<TranslateWordState> =
        MutableStateFlow(TranslateWordState.Started)
    val translateWordStateFlow: StateFlow<TranslateWordState> = _translateWordStateFlow

    fun getTranslateWordFlow(word: String)= viewModelScope.launch {
        _translateWordStateFlow.value = TranslateWordState.Loading

        repository.getTranslateWordAsync(word)
            .collect {
                _translateWordStateFlow.value = it
            }

        Log.d("myLogs", "ViewModel Thread: ${Thread.currentThread().name}")
    }

}

