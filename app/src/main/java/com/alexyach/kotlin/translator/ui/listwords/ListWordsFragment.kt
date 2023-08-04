package com.alexyach.kotlin.translator.ui.listwords

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.alexyach.kotlin.translator.R
import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel
import com.alexyach.kotlin.translator.databinding.FragmentListWordsBinding
import com.alexyach.kotlin.translator.ui.base.BaseFragment
import com.alexyach.kotlin.translator.ui.base.UIState
import com.alexyach.kotlin.translator.ui.translate.TranslateFragment
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ListWordsFragment
    : BaseFragment<FragmentListWordsBinding, ListWordsViewModel>() {

    override val viewModel by  viewModels<ListWordsViewModel>()

    private lateinit var adapter: ListWordsAdapter
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListWordsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setClickListener()
        observeStateFlow()
    }

    private fun setClickListener() {

        binding.searchEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(symbol: CharSequence?, start: Int, before: Int, count: Int) {

                viewModel.searchWord(symbol.toString())

                // Видаляємо символ пошуку (SEARCH_SYMBOL)
                if (count == 0) {
                    viewModel.removeSearchSymbol()
                }
            }
        })
    }


    private fun observeStateFlow() {
        viewModel.listWordsStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach {
                responseState(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun responseState(state: UIState<List<WordsEntityModel>>) {
        when(state) {
            is UIState.Success -> renderDataList(state.data)
            is UIState.Error -> showError(state.message)
            UIState.Loading -> showLoading()
            UIState.Started -> {}
        }
    }

    private fun renderDataList(dataList: List<WordsEntityModel>) {
        binding.listWordsProgressbar.visibility = View.GONE
        setupAdapter(dataList)
    }

    private fun showError(message: String?) {
        binding.listWordsProgressbar.visibility = View.GONE
        setupAdapter(listOf(
            WordsEntityModel(
                id = 0,
                wordInit = "Error",
                wordTranslate = message ?: "message = null"
            )
        ))
    }

    private fun showLoading() {
        binding.listWordsProgressbar.visibility = View.VISIBLE
    }

    private fun setupAdapter(dataList: List<WordsEntityModel>) {
        adapter = ListWordsAdapter(dataList) {word ->
            viewModel.deleteWord(word)
        }
        binding.listWordRecycler.adapter = adapter
    }

    private fun setupToolbar() {
        with(binding.toolbarListWords) {
//            title = "Words"
            inflateMenu(R.menu.list_words_menu)

            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.delete_all -> {
                        viewModel.deleteAll()
                        true
                    }

                    /*R.id.to_translate_words -> {
                        toTranslateWordFragment()
                        true
                    }*/

                    else -> false
                }
            }
        }
    }

    private fun toTranslateWordFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, TranslateFragment.newInstance())
//            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = ListWordsFragment()
    }
}