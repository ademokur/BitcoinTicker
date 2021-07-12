package com.aokur.bitcointicker.di

import android.content.Context
import androidx.room.Room
import com.aokur.bitcointicker.data.db.CoinDatabase
import com.aokur.bitcointicker.data.db.dao.CoinDao
import com.aokur.bitcointicker.util.Constants
import com.aokur.bitcointicker.util.PrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CoinDatabase =
        Room.databaseBuilder(context, CoinDatabase::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCryptoDao(database: CoinDatabase): CoinDao = database.coinDao()


    @Provides
    @Singleton
    fun providePrefManager(@ApplicationContext context: Context): PrefManager = PrefManager(context)

}