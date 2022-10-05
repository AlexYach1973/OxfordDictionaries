package com.alexyach.kotlin.translator.ui.translate

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.translator.retrofit.ITranslateRepository
import com.alexyach.kotlin.translator.retrofit.RetrofitImpl
import kotlinx.coroutines.launch

class TranslateViewModel : ViewModel() {

    private val repository: ITranslateRepository = RetrofitImpl()

    /** LiveData */
    private val translateWordState: MutableLiveData<TranslateWordState> =
        MutableLiveData<TranslateWordState>()

    fun getTranslateWordStateLiveData(): MutableLiveData<TranslateWordState> {
        return translateWordState
    }

    fun getTranslateWord(word: String) = viewModelScope.launch {

        translateWordState.value = TranslateWordState.Loading
        translateWordState.value= repository.getTranslateWordAsync(word)

        Log.d("myLogs", "ViewModel, Thread: ${Thread.currentThread().name}")

           /* Log.d("myLogs", "ViewModel, response: ${
                response.results[0].lexicalEntries[0].entries[0].senses[0].translations
            }")*/

    }

}