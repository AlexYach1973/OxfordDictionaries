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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.alexyach.kotlin.translator.databinding.FragmentTranslateBinding
import com.alexyach.kotlin.translator.domain.model.Language
import com.alexyach.kotlin.translator.domain.model.WordTranslateModel
import com.alexyach.kotlin.translator.ui.base.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TranslateFragment : BaseFragment<FragmentTranslateBinding,
        TranslateViewModel>() {

    private lateinit var textShow: String
    private var language = Language.En

    // wav
    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var adapter: TranslateAdapter

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
        viewModel.translateWordStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { responseState(it) }
            .launchIn(lifecycleScope)

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
        binding.buttonSound.setOnClickListener {
            playSound()
        }
    }

    private fun responseState(state: TranslateWordState) {
        when (state) {
            is TranslateWordState.Success -> {
                renderSuccessData(state.wordModel)
            }

            is TranslateWordState.Error -> {
                renderError(state)
            }

            TranslateWordState.Loading -> {
                showLoading()
                hideSound()
            }

            TranslateWordState.Started -> {
            }
        }
    }

    private fun setupAdapter(wordModel: WordTranslateModel) {
        adapter = TranslateAdapter(wordModel)
        binding.translateRecyclerView.adapter = adapter
    }

    private fun renderSuccessData(wordModel: WordTranslateModel) {
        setupAdapter(wordModel)
        setupSound(wordModel.soundPath)
        showText()
    }

    private fun renderError(state: TranslateWordState.Error) {
        textShow = ""
        setupAdapter(
            WordTranslateModel(
                listOf("${state.error.message}"),
                listOf("")
            )
        )
        hideSound()
        showText()
    }


    // Log
//        Log.d("myLogs", "lexicalEntries: ${wordDto.results[0].lexicalEntries.size}")
//        Log.d("myLogs", "entries: ${wordDto.results[0].lexicalEntries[0].entries.size}")
//        Log.d("myLogs", "senses: ${wordDto.results[0].lexicalEntries[0].entries[0].senses.size}")
//        Log.d("myLogs", "translations: ${wordDto.results[0].lexicalEntries[0].entries[0].senses[0].translations.size}")
//        Log.d("myLogs", "examples: ${wordDto.results[0].lexicalEntries[0].entries[0].senses[0].examples.size}")
//        Log.d("myLogs", "senses: ${wordDto.results[0].lexicalEntries[0].entries[0].senses[0].examples[0].text}")

    private fun setupSound(soundPath: String) {
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
        binding.translateRecyclerView.visibility = View.GONE
    }

    private fun showText() {
        binding.progressBar.visibility = View.GONE
        binding.translateRecyclerView.visibility = View.VISIBLE
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