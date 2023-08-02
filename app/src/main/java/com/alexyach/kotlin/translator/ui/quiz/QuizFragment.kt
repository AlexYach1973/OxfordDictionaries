package com.alexyach.kotlin.translator.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.alexyach.kotlin.translator.App
import com.alexyach.kotlin.translator.R
import com.alexyach.kotlin.translator.databinding.FragmentQuizBinding
import com.alexyach.kotlin.translator.domain.model.QuizModel
import com.alexyach.kotlin.translator.ui.base.BaseFragment
import com.alexyach.kotlin.translator.ui.base.UIState
import com.alexyach.kotlin.translator.utils.viewModelCreator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class QuizFragment : BaseFragment<FragmentQuizBinding, QuizViewModel>() {

    private lateinit var adapter: QuizAdapter

    override val viewModel: QuizViewModel by viewModelCreator {
        QuizViewModel(
            (requireActivity().application as App).database
        )
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

        // Count
        viewModel.countGuessWordFlow
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.countGuessWord.text = it.toString()
            }
            .launchIn(lifecycleScope)

        viewModel.countNoGuessWordFlow
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.countNoGuessWord.text = it.toString()
            }
            .launchIn(lifecycleScope)

    }

    private fun responseState(state: UIState<List<QuizModel>>) {
        when (state) {
            is UIState.Success -> renderUi(state.data)
            is UIState.Error -> showError(state.message)
            UIState.Loading -> showLoading()
            UIState.Started -> {} //
        }
    }

    private fun renderUi(dataList: List<QuizModel>) {
        setupAdapter(dataList)
        binding.quizFragmentProgressbar.visibility = View.GONE
        binding.llQuizFragment.visibility = View.VISIBLE
    }

    private fun showInitWord(word: String) {
        binding.wordQuizTextView.text = word
    }

    private fun setupAdapter(dataList: List<QuizModel>) {
        adapter = QuizAdapter(dataList) { word ->
            viewModel.guessWord(dataList, word)
        }
        binding.quizRecyclerView.adapter = adapter
    }

    private fun showError(error: String) {
        setupAdapter(
            listOf(
                QuizModel(
                    id = 0,
                    wordInit = error,
                    wordTranslate = "Помилка зчитування бази даних"
                )
            )
        )
        binding.quizFragmentProgressbar.visibility = View.GONE
        binding.llQuizFragment.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.quizFragmentProgressbar.visibility = View.VISIBLE
        binding.llQuizFragment.visibility = View.GONE
    }

    private fun setupToolBar() {
        with(binding.toolbarQuiz) {
            title = getString(R.string.title_quiz_toolbar)
        }
    }

    companion object {
        fun newInstance() = QuizFragment()
    }

}