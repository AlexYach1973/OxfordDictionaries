package com.alexyach.kotlin.translator.ui.listwords

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.alexyach.kotlin.translator.App
import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel
import com.alexyach.kotlin.translator.databinding.FragmentListWordsBinding
import com.alexyach.kotlin.translator.ui.base.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListWordsFragment
    : BaseFragment<FragmentListWordsBinding, ListWordsViewModel>() {

    override val viewModel: ListWordsViewModel by lazy {
        ViewModelProvider(
            this, ListWordsViewModel.getViewModelFactory(
                (requireActivity().application as App).database
            )
        )[ListWordsViewModel::class.java]
    }

    private lateinit var adapter: ListWordsAdapter
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListWordsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
        observeStateFlow()
    }

    private fun setClickListener() {

        binding.searchEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(symbol: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchWord(symbol.toString())
//                Log.d("myLogs", "onTextChanged: symbol: $symbol")
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

    private fun responseState(state: ListWordsState) {
        when(state) {
            is ListWordsState.Success -> renderDataList(state.dataList)
            is ListWordsState.Error -> showError(state.error.message)
            ListWordsState.Loading -> showLoading()
            ListWordsState.Started -> {}
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

    companion object {
        fun newInstance() = ListWordsFragment()
    }
}