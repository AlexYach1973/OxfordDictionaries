package com.alexyach.kotlin.translator.di

import android.content.Context
import com.alexyach.kotlin.translator.ui.listwords.ListWordsFragment
import com.alexyach.kotlin.translator.ui.quiz.QuizFragment
import com.alexyach.kotlin.translator.ui.translate.TranslateFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataBaseModule::class,
        RepositoryModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(quizFragment: QuizFragment)
    fun inject(listWordsFragment: ListWordsFragment)
    fun inject(translateFragment: TranslateFragment)
}