package com.aokur.bitcointicker

import com.bumptech.glide.Glide
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        // To improve the performance of Glide, call Glide.get() on a background thread.
        // It will initialize the library and the first call on the list will be faster.
        Schedulers.io().scheduleDirect {
            Glide.get(this)
        }
    }
}