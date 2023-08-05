package com.alexyach.kotlin.translator

import android.app.Application
import com.alexyach.kotlin.translator.data.local.database.AppDatabase
import com.alexyach.kotlin.translator.di.databaseModule
import com.alexyach.kotlin.translator.di.networkModule
import com.alexyach.kotlin.translator.di.repositoryModule
import com.alexyach.kotlin.translator.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(
                viewModelModule, networkModule, databaseModule, repositoryModule
            ))
        }
    }

}