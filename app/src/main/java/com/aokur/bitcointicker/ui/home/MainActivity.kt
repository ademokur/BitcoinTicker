package com.aokur.bitcointicker.ui.home

import android.os.Bundle
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.databinding.ActivityMainBinding
import com.aokur.bitcointicker.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun observeViewModel() {

    }
}