package com.alexyach.kotlin.translator.ui.translate

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.alexyach.kotlin.translator.App
import com.alexyach.kotlin.translator.R
import com.alexyach.kotlin.translator.databinding.FragmentTranslateBinding
import com.alexyach.kotlin.translator.domain.model.Language
import com.alexyach.kotlin.translator.domain.model.WordTranslateModel
import com.alexyach.kotlin.translator.ui.base.BaseFragment
import com.alexyach.kotlin.translator.ui.base.UIState
import com.alexyach.kotlin.translator.ui.listwords.ListWordsFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TranslateFragment : BaseFragment<FragmentTranslateBinding,
        TranslateViewModel>() {

    private lateinit var textShow: String
    private var language = Language.En

    // wav
    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var adapter: TranslateAdapter
    private var wordTranslateModel = WordTranslateModel()

    @Inject
    override lateinit var viewModel: TranslateViewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTranslateBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Dagger
        (requireActivity().application as App).appComponent.inject(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        setupClickListener()
    }

    private fun observe() {
        viewModel.translateWordStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { responseState(it) }
            .launchIn(lifecycleScope)

        viewModel.toastMessage
            .flowWithLifecycle(lifecycle)
            .onEach { showToast(it) }
            .launchIn(lifecycleScope)

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
            viewModel.getTranslateWordFlow(binding.etInitialWord.text.toString().trim(), language)
            hideKeyboard()
        }

        // Button Sound
        binding.buttonSound.setOnClickListener {
            playSound()
        }

        // Button Save word
        binding.saveWordBtn.setOnClickListener {
            val initWord = binding.etInitialWord.text.toString().trim()
            if (initWord.isEmpty()
                || wordTranslateModel.translateWordList.isEmpty()) {
                toast("Порожнє поле")
            } else {
                viewModel.insertWordToDatabase(
                    initWord,
                    wordTranslateModel
                )
                hideFabSave()
//                toListWordsFragment()
            }
        }

        // Init Word Edit Text
        binding.etInitialWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {} // Не використовуєм
            override fun afterTextChanged(p0: Editable?) {} // Не використовуєм

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                hideFabSave()
            }

        })
    }

    private fun showToast(toastMessage: Int) {
        when(toastMessage) {
            1 -> toast("Таке слово вже є")
            else -> {}
        }
    }
    private fun responseState(state: UIState<WordTranslateModel>) {
        when (state) {
            is UIState.Success -> {
                renderSuccessData(state.data)
            }

            is UIState.Error -> {
                renderError(state.message)
            }

            UIState.Loading -> {
                showLoading()
                hideSound()
            }

            UIState.Started -> {
                hideFabSave()
            }
        }
    }

    private fun setupAdapter(wordModel: WordTranslateModel) {
        adapter = TranslateAdapter(wordModel)
        binding.translateRecyclerView.adapter = adapter
    }

    private fun renderSuccessData(wordModel: WordTranslateModel) {
        wordTranslateModel = wordModel
        setupAdapter(wordModel)
        setupSound(wordModel.soundPath)
        showText()
        showFabSave()
    }

    private fun renderError(message: String) {
        textShow = ""
        setupAdapter(
            WordTranslateModel(
                listOf(message),
                listOf("")
            )
        )
        hideSound()
        showText()
        hideFabSave()
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
        hideFabSave()
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

    private fun hideFabSave() {
        binding.saveWordBtn.hide()
    }
    private fun showFabSave() {
        binding.saveWordBtn.show()
    }

    private fun toListWordsFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListWordsFragment.newInstance())
//            .addToBackStack(null)
            .commit()
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