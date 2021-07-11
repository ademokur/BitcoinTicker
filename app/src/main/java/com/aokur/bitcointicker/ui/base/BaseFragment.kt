package com.aokur.bitcointicker.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.aokur.bitcointicker.App
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VDB : ViewDataBinding, VM : BaseViewModel> : DaggerFragment(),
    LifecycleObserver {
    lateinit var binding: VDB

    lateinit var viewModel: VM

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    abstract val TAG: String

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun getViewModel(): Class<VM>

    abstract fun init(savedInstanceState: Bundle?)

    abstract fun observeViewModel()

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = generateViewModel()

        init(savedInstanceState)
        observeViewModel()
    }

    fun getApp(): App? {
        return (activity as BaseActivity<*, *>?)?.getApp()
    }

    fun isViewModelInitialized() = ::viewModel.isInitialized

    open fun generateViewModel(): VM {
        return ViewModelProvider(this, factory).get(getViewModel())
    }
}