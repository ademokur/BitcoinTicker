package com.aokur.bitcointicker.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.aokur.bitcointicker.App
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> : DaggerAppCompatActivity() {
    lateinit var binding: VDB

    lateinit var viewModel: VM

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun getViewModel(): Class<VM>

    abstract fun init(savedInstanceState: Bundle?)

    abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        init(savedInstanceState)
        observeViewModel()
    }

    fun getApp(): App {
        return application as App
    }
}