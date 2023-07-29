package com.alexyach.kotlin.translator.ui.listwords

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexyach.kotlin.translator.data.local.database.WordsEntityModel
import com.alexyach.kotlin.translator.databinding.DatabaseRecyclerItemBinding
import com.alexyach.kotlin.translator.domain.model.WordsDatabaseModel

class ListWordsAdapter(
    private val dataList: List<WordsEntityModel>,
    private val click: (word : WordsEntityModel) -> Unit
) : RecyclerView.Adapter<ListWordsAdapter.ListWordsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListWordsViewHolder {
        val binding = DatabaseRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ListWordsViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ListWordsViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    // View Holder
    inner class ListWordsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = DatabaseRecyclerItemBinding.bind(itemView)
        fun bind(item: WordsEntityModel) {
            binding.databaseWordItem.text = item.wordInit
            binding.databaseTranslateItem.text = item.wordTranslate

            binding.deleteBtn.setOnClickListener {
                click(item)
            }
        }

    }

    /*companion object {
        private val DiffCallback =
            object : DiffUtil.ItemCallback<WordsEntityModel>() {
                override fun areItemsTheSame(
                    oldItem: WordsEntityModel,
                    newItem: WordsEntityModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: WordsEntityModel,
                    newItem: WordsEntityModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }*/
}

