package com.aokur.bitcointicker.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.databinding.ActivityMainBinding
import com.aokur.bitcointicker.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}