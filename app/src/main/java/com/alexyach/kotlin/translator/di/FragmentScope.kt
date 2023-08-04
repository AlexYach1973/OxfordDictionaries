package com.alexyach.kotlin.translator.di

import javax.inject.Scope

/**
 * За домогою FragmentScope анотації
 *  ми локалізували Singleton provideRetrofit()
 *  областю видимості TranslateFragment, який
 *  створює TranslateViewModel в областi видимості FragmentScope*/
@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope