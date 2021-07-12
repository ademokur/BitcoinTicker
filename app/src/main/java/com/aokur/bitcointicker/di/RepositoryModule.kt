package com.aokur.bitcointicker.di

import com.aokur.bitcointicker.data.repository.CoinRepository
import com.aokur.bitcointicker.data.repository.CoinRepositoryImpl
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
    abstract fun provideCoinRepository(repository: CoinRepositoryImpl): CoinRepository
}