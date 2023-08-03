package com.alexyach.kotlin.translator.di.modules

import com.alexyach.kotlin.translator.data.local.DatabaseImpl
import com.alexyach.kotlin.translator.data.retrofit.RetrofitImpl
import com.alexyach.kotlin.translator.domain.interfaces.IDatabaseRepository
import com.alexyach.kotlin.translator.domain.interfaces.IRemoteRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRoomRepo(
        roomRepository: DatabaseImpl
    ) : IDatabaseRepository

    @Binds
    @Singleton
    abstract fun provideRemoteRepo(
        remoteRepository: RetrofitImpl
    ): IRemoteRepository
}