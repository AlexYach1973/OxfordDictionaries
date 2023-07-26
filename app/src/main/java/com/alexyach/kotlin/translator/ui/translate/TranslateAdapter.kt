package com.alexyach.kotlin.translator.ui.translate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexyach.kotlin.translator.databinding.TranslateRecyclerItemBinding
import com.alexyach.kotlin.translator.domain.model.WordTranslateModel

class TranslateAdapter(private val wordModel: WordTranslateModel)
    : RecyclerView.Adapter<TranslateAdapter.TranslateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslateViewHolder {
        val binding =
            TranslateRecyclerItemBinding.inflate(LayoutInflater.from(parent.context))
        return TranslateViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return wordModel.translateWordList.size
    }

    override fun onBindViewHolder(holder: TranslateViewHolder, position: Int) {
        holder.bind(position)
    }

    // ViewHolder
    inner class TranslateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TranslateRecyclerItemBinding.bind(itemView)

        fun bind(index: Int) {
            binding.translateWord.text = wordModel.translateWordList[index]
            binding.exampleWord.text = wordModel.exampleWordList[index]
        }
    }

}