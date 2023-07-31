package com.alexyach.kotlin.translator.ui.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.alexyach.kotlin.translator.App
import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel
import com.alexyach.kotlin.translator.databinding.FragmentQuizBinding
import com.alexyach.kotlin.translator.domain.model.QuizModel
import com.alexyach.kotlin.translator.ui.base.BaseFragment
import com.alexyach.kotlin.translator.ui.listwords.ListWordsState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class QuizFragment : BaseFragment<FragmentQuizBinding, QuizViewModel>() {

    private lateinit var adapter: QuizAdapter

    override val viewModel: QuizViewModel by lazy {
        ViewModelProvider(
            this,
            QuizViewModel.getViewModelFactory(
                (requireActivity().application as App).database
            )
        )[QuizViewModel::class.java]
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentQuizBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolBar()
        observeFlow()

    }

    private fun observeFlow() {
        viewModel.listWordsStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach {
                responseState(it)
            }
            .launchIn(lifecycleScope)

        viewModel.initWordFlow
            .flowWithLifecycle(lifecycle)
            .onEach {
                showInitWord(it.wordInit)
            }
            .launchIn(lifecycleScope)


    }
    private fun responseState(state: QuizState) {
        when(state) {
            is QuizState.Success -> renderUi(state.dataList)
            is QuizState.Error -> {}
            QuizState.Loading -> {}
            QuizState.Started -> {}
        }
    }

    private fun renderUi(dataList: List<QuizModel>) {
        setupAdapter(dataList)
//        Log.d("myLogs", "QuizFragment dataList: ${dataList.size}")
    }
    private fun showInitWord(word: String) {
        binding.wordQuizTextView.text = word
    }

    private fun setupAdapter(dataList: List<QuizModel>) {
        adapter = QuizAdapter(dataList){word ->
            viewModel.guessWord(dataList, word)
        }
        binding.quizRecyclerView.adapter = adapter
    }

    private fun setupToolBar() {

    }
    companion object {
        fun newInstance() = QuizFragment()
    }

}