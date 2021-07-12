package com.aokur.bitcointicker.ui.home.favorite

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.databinding.FragmentFavouriteCoinsBinding
import com.aokur.bitcointicker.ui.base.BaseFragment
import com.aokur.bitcointicker.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteCoinsFragment : BaseFragment<FragmentFavouriteCoinsBinding>() {

    private var favouriteCoinAdapter = FavouriteCoinsAdapter()
    private val favouriteCoinViewModel: FavouriteCoinsViewModel by viewModels()

    override val TAG: String
        get() = FavouriteCoinsFragment::class.java.simpleName

    override fun getLayoutRes(): Int {
        return R.layout.fragment_favourite_coins
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.state = FavouriteCoinsViewState(Status.LOADING)

        binding.rvFavouriteCoinList.adapter = favouriteCoinAdapter

        favouriteCoinAdapter.setItemOnClickListener {
            findNavController().navigate(
                FavouriteCoinsFragmentDirections.actionFavouriteCoinsFragmentToCoinDetailFragment(
                    it
                )
            )
        }

        favouriteCoinViewModel.getAllFavourites()

        favouriteCoinViewModel.favouriteCoins.observe(viewLifecycleOwner) {
            favouriteCoinAdapter.submitList(it)
        }

        favouriteCoinViewModel.coinState.observe(viewLifecycleOwner) {
            val status = if (it) Status.SUCCESS else Status.ERROR
            binding.state = FavouriteCoinsViewState(status)
        }
    }
}