package com.aokur.bitcointicker.di.module

import com.aokur.bitcointicker.di.scope.ActivityScope
import com.aokur.bitcointicker.ui.home.MainActivity
import com.aokur.bitcointicker.ui.home.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity

}