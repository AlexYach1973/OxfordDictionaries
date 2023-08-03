package com.alexyach.kotlin.translator.di

import android.content.Context
import com.alexyach.kotlin.translator.di.modules.AppSubcomponent
import com.alexyach.kotlin.translator.di.modules.DataBaseModule
import com.alexyach.kotlin.translator.di.modules.NetworkModule
import com.alexyach.kotlin.translator.di.modules.RepositoryModule
import com.alexyach.kotlin.translator.ui.listwords.ListWordsFragment
import com.alexyach.kotlin.translator.ui.quiz.QuizFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataBaseModule::class,
        RepositoryModule::class,
        NetworkModule::class,
        AppSubcomponent::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun remoteComponent(): RemoteComponent.Factory

    fun inject(quizFragment: QuizFragment)
    fun inject(listWordsFragment: ListWordsFragment)



//    fun inject(translateFragment: TranslateFragment)
}