package com.alexyach.kotlin.translator

import android.app.Application
import com.alexyach.kotlin.translator.data.local.database.AppDatabase

class App : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}