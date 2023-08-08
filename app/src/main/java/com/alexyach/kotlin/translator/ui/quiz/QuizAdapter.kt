package com.alexyach.kotlin.translator.ui.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.alexyach.kotlin.translator.R
import com.alexyach.kotlin.translator.databinding.QuizRecyclerItemBinding
import com.alexyach.kotlin.translator.domain.model.QuizModel

class QuizAdapter(
    private val dataList: List<QuizModel>,
    private val click: (word: QuizModel) -> Unit
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    /** Click Position */
    var selectItem: Int = Int.MIN_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = QuizRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return QuizViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    // ViewHolder
    inner class QuizViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = QuizRecyclerItemBinding.bind(itemView)

        fun bind(item: QuizModel) {
            binding.quizTextViewItem.text = item.wordTranslate

            if (!item.isGuess && selectItem == adapterPosition) {
                // Анімація для не відгаданого слова
                itemView.animation =
                    AnimationUtils.loadAnimation(
                        itemView.context, R.anim.anim_quiz_recycler_item_guess
                    )

                binding.llQuizRecyclerItem.background =
                    ResourcesCompat.getDrawable(
                        itemView.context.resources, R.drawable.gradient_error, null
                    )
                selectItem = Int.MIN_VALUE
                // Простро не відгадане слово
            } else if (!item.isGuess) {
                binding.llQuizRecyclerItem.background =
                    ResourcesCompat.getDrawable(
                        itemView.context.resources, R.drawable.gradient_error, null
                    )
            } else {
                binding.llQuizRecyclerItem.background =
                    ResourcesCompat.getDrawable(
                        itemView.context.resources, R.drawable.gradient_cardview, null
                    )

            }

            itemView.setOnClickListener {
                if (item.isGuess) {
                    click(item)
                    notifyItemChanged(adapterPosition)
                    selectItem = adapterPosition
                }
                notifyDataSetChanged()
            }
        }
    }
}