package com.alexyach.kotlin.translator.ui.translate

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.alexyach.kotlin.translator.databinding.FragmentTranslateBinding
import com.alexyach.kotlin.translator.retrofit.modelDto.Translation
import com.alexyach.kotlin.translator.retrofit.modelDto.WordTranslate
import com.alexyach.kotlin.translator.ui.base.BaseFragment

class TranslateFragment : BaseFragment<FragmentTranslateBinding,
        TranslateViewModel>() {

    override val viewModel: TranslateViewModel by lazy {
        ViewModelProvider(this)[TranslateViewModel::class.java]
    }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTranslateBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Спостереження */
        viewModel.getTranslateWordStateLiveData().observe(viewLifecycleOwner){
            responseState(it)
        }

        binding.btnTranslate.setOnClickListener {
            viewModel.getTranslateWord(binding.etInitialWord.text.toString())
            hideKeyboard()
        }


    }

    private fun responseState(state: TranslateWordState) {
        when(state) {
            is TranslateWordState.SuccessTranslateWord -> {
                renderData(state.word)
                showText()
            }

            is TranslateWordState.ErrorTranslateWord -> {
                showTextWordTranslate(listOf(state.error.message!!))
                showText()
            }

            TranslateWordState.Loading -> {
                showLoading()
            }
        }
    }

    private fun renderData(word: WordTranslate) {
        val translateWord: List<Translation>

//        Log.d("myLogs", "Fragment, response: ${word}")

        word.let {
            translateWord =
                it.results[0].lexicalEntries[0].entries[0].senses[0].translations
        }

        showTextWordTranslate(translateWord.map { it.text })

    }

    private fun showTextWordTranslate(wordList: List<String>) {
        var textStr = " ○ "

        for (i in 0 until wordList.size - 1) {
            textStr += "${wordList[i]} \n ○ "
        }
        textStr += wordList.last()
        binding.tvTranslatedWord.text = textStr

//        Log.d("myLogs", "wordList: $textStr")

    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun showText() {
        binding.progressBar.visibility = View.GONE
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }


    companion object {
        fun newInstance() = TranslateFragment()
    }
}