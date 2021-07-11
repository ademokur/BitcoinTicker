package com.aokur.bitcointicker.di.module

import androidx.lifecycle.ViewModelProvider
import com.aokur.bitcointicker.ui.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}