package com.alexyach.kotlin.translator.ui.translate

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexyach.kotlin.translator.databinding.FragmentTranslateBinding
import com.alexyach.kotlin.translator.model.Language
import com.alexyach.kotlin.translator.retrofit.modelDto.Sense
import com.alexyach.kotlin.translator.retrofit.modelDto.WordTranslate
import com.alexyach.kotlin.translator.ui.base.BaseFragment
import kotlinx.coroutines.launch

class TranslateFragment : BaseFragment<FragmentTranslateBinding,
        TranslateViewModel>() {

    private lateinit var textShow: String
    private var language = Language.En

    // wav
    private lateinit var mediaPlayer: MediaPlayer

    override val viewModel: TranslateViewModel by lazy {
        ViewModelProvider(this)[TranslateViewModel::class.java]
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTranslateBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Observe StateFlow */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.translateWordStateFlow.collect {
                    responseState(it)
                }
            }
        }

        setupClickListener()
    }

    private fun setupClickListener() {

        // switch Language
        binding.switchLanguage
            .setOnCheckedChangeListener { _, isChecked ->
                language = if (isChecked) {
                    Language.En
                } else {
                    Language.Ru
                }
            }

        // FAB translate
        binding.fabTranslate.setOnClickListener {
            viewModel.getTranslateWordFlow(binding.etInitialWord.text.toString(), language)
            hideKeyboard()
        }

        // Button Sound
        binding.buttonSound.setOnClickListener{
            playSound()
        }

    }

    private fun responseState(state: TranslateWordState) {
        when (state) {
            is TranslateWordState.SuccessTranslateWord -> {
                renderData(state.word)
                showText()
            }

            is TranslateWordState.ErrorTranslateWord -> {
                renderError(state)
                hideSound()
                showText()
            }

            TranslateWordState.Loading -> {
                showLoading()
                hideSound()
            }
            TranslateWordState.Started -> {
//                Log.d("myLogs", "WordState: Started")
            }
        }
    }

    private fun renderError(state: TranslateWordState.ErrorTranslateWord) {
        textShow = ""
        showTextWordTranslate(listOf(state.error.message!!), "???")
    }

    private fun renderData(wordDto: WordTranslate) {
        textShow = ""

//        Log.d("myLogs", "Fragment, response: ${wordDto}")

        setupSound(wordDto)

        val wordDtoSens: List<Sense>? = wordDto.results[0].lexicalEntries[0].entries[0].senses

        wordDtoSens?.forEach { sens ->
            val translationList = sens.translations

            // Words
            if (!sens.translations.isNullOrEmpty()) {
                val currentWord: List<String>? = sens.translations.map { it.text }

                if (!currentWord.isNullOrEmpty()) {
                    showTextWordTranslate(currentWord, "ПЕРЕКЛАД:")
                } else {
                    Log.d("myLogs", "currentWord == null")
                }

            } else {
                showTextWordTranslate(listOf("Не має"), "ПЕРЕКЛАД:")
                Log.d("myLogs", "translationList == null")
            }

            // Examples
            if (!sens.examples.isNullOrEmpty()) {
                val currentExample: List<String>? = sens.examples.map { it.text }

                if (!currentExample.isNullOrEmpty()) {
                    showTextWordTranslate(currentExample, "ВИКОРИСТАННЯ:")
                } else {
                    Log.d("myLogs", "currentExample == null")
                }
            } else {
                showTextWordTranslate(listOf("Не має"), "ВИКОРИСТАННЯ:")
                Log.d("myLogs", "examples == null")
            }
        }


        // Log
        /*Log.d("myLogs", "Result: ${wordDto.results.size}")
        Log.d("myLogs", "lexicalEntries: ${wordDto.results[0].lexicalEntries.size}")
        Log.d("myLogs", "entries: ${wordDto.results[0].lexicalEntries[0].entries.size}")
        Log.d("myLogs", "senses: ${wordDto.results[0].lexicalEntries[0].entries[0].senses.size}")
//        Log.d("myLogs", "translations: ${wordDto.results[0].lexicalEntries[0].entries[0].senses[0].translations.size}")
        Log.d("myLogs", "examples: ${wordDto.results[0].lexicalEntries[0].entries[0].senses[0].examples.size}")
        Log.d("myLogs", "senses: ${wordDto.results[0].lexicalEntries[0].entries[0].senses[0].examples[0].text}")*/

        /*Log.d(
            "myLogs",
            "senses: ${wordDto.results[0].lexicalEntries[0].entries[0].pronunciations[0].audioFile}"
        )*/

    }

    private fun showTextWordTranslate(wordList: List<String>, title: String) {

        textShow += "\n ------------- \n $title  "

        for (element in wordList) {
            textShow += "\n○ ${element} "
        }
        binding.tvTranslatedWord.text = textShow
    }

    private fun setupSound(wordDto: WordTranslate) {
        val soundPath: String? = wordDto?.results?.get(0)?.lexicalEntries?.get(0)?.entries?.get(0)?.pronunciations?.get(0)?.audioFile

        if (!soundPath.isNullOrEmpty()) {
            showSound()
            mediaPlayer = MediaPlayer.create(requireContext(), Uri.parse(soundPath))

        } else {
            hideSound()
            Log.d("myLogs", "soundPath != null")
        }
    }

    private fun playSound() {
        mediaPlayer.start()
    }


    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showText() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showSound() {
        binding.buttonSound.visibility = View.VISIBLE
    }
    private fun hideSound() {
        binding.buttonSound.visibility = View.INVISIBLE
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