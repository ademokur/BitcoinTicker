package com.aokur.bitcointicker.ui.home

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
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
        setSupportActionBar(binding.toolbar)
        navController = findNavController(R.id.nav_host_fragment)

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu_bottom)
        val menu = popupMenu.menu

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.coinFragment || destination.id == R.id.favouriteCoinsFragment) {
                showBottomNavigation()
                supportActionBar?.apply {
                    show()
                }
            } else {
                hideBottomNavigation()
                supportActionBar?.apply {
                    hide()
                }
            }
        }

        binding.bottomBar.setupWithNavController(menu, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun hideBottomNavigation() {
        binding.bottomBar.visibility = View.GONE
    }

    fun showBottomNavigation() {
        binding.bottomBar.visibility = View.VISIBLE
    }
}