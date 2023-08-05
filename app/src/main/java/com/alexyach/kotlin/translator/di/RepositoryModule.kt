package com.alexyach.kotlin.translator.di

import com.alexyach.kotlin.translator.data.local.DatabaseImpl
import com.alexyach.kotlin.translator.data.retrofit.RetrofitImpl
import com.alexyach.kotlin.translator.domain.interfaces.IDatabaseRepository
import com.alexyach.kotlin.translator.domain.interfaces.IRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRoomRepo(
        roomRepository: DatabaseImpl
    ) : IDatabaseRepository

    @Binds
    @Singleton
    abstract fun provideRetrofitRepo(
        remoteRepository: RetrofitImpl
    ): IRemoteRepository

}