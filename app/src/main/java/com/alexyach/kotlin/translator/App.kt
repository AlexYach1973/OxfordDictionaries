package com.alexyach.kotlin.translator

import android.app.Application
import com.alexyach.kotlin.translator.data.local.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}