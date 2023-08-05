package com.alexyach.kotlin.translator.di

import com.alexyach.kotlin.translator.ui.listwords.ListWordsViewModel
import com.alexyach.kotlin.translator.ui.quiz.QuizViewModel
import com.alexyach.kotlin.translator.ui.translate.TranslateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel<QuizViewModel> {
        QuizViewModel(roomRepository = get())
    }

    viewModel<TranslateViewModel> {
        TranslateViewModel(
            roomRepository = get(),
            remoteRepository = get()
        )
    }

    viewModel<ListWordsViewModel> {
        ListWordsViewModel(roomRepository = get())
    }


}