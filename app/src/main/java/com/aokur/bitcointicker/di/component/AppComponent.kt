package com.aokur.bitcointicker.di.component

import android.app.Application
import com.aokur.bitcointicker.App
import com.aokur.bitcointicker.di.module.ActivityModule
import com.aokur.bitcointicker.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityModule::class,
        ViewModelModule::class,
        AndroidInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}