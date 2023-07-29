package com.alexyach.kotlin.translator.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexyach.kotlin.translator.R
import com.alexyach.kotlin.translator.databinding.FragmentHomepageBinding
import com.alexyach.kotlin.translator.ui.listwords.ListWordsFragment
import com.alexyach.kotlin.translator.ui.translate.TranslateFragment

class HomepageFragment : Fragment() {

    private var _binding: FragmentHomepageBinding? = null
    private val binding: FragmentHomepageBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
    }

    private fun setupClickListener() {
        with(binding) {
            translateFragmentBtn.setOnClickListener{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TranslateFragment.newInstance())
                    .addToBackStack("TranslateFragment")
                    .commit()
            }

            listWordFragmentBtn.setOnClickListener{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListWordsFragment.newInstance())
                    .addToBackStack("ListWordsFragment")
                    .commit()
            }

            quizFragmentBtn.setOnClickListener{

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        fun newInstance() = HomepageFragment()
    }
}