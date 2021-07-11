package com.aokur.bitcointicker.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.databinding.ActivityMainBinding
import com.aokur.bitcointicker.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {

    }
}