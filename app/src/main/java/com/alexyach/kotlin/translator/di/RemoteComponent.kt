package com.alexyach.kotlin.translator.di

import com.alexyach.kotlin.translator.ui.translate.TranslateFragment
import dagger.Subcomponent

@Subcomponent
interface RemoteComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RemoteComponent
    }
    fun inject(translateFragment: TranslateFragment)

}