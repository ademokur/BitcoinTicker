package com.aokur.bitcointicker.di

import com.aokur.bitcointicker.BuildConfig
import com.aokur.bitcointicker.data.remote.CoinApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_URL)
            .build()


    @Provides
    @Singleton
    fun provideCryptoService(retrofit: Retrofit): CoinApiService =
        retrofit.create(CoinApiService::class.java)
}