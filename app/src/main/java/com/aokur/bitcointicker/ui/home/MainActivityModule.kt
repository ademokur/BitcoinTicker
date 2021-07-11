package com.aokur.bitcointicker.ui.home

import androidx.lifecycle.ViewModel
import com.aokur.bitcointicker.ui.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityModule {
    // View models
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainActivityViewModel(mainActivityViewModel: MainViewModel): ViewModel
}