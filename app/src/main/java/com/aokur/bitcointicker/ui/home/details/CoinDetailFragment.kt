package com.aokur.bitcointicker.ui.home.details

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.data.model.CoinDetailItem
import com.aokur.bitcointicker.databinding.FragmentCoinDetailBinding
import com.aokur.bitcointicker.ui.base.BaseFragment
import com.aokur.bitcointicker.ui.home.MainActivity
import com.aokur.bitcointicker.util.PrefManager
import com.aokur.bitcointicker.util.Status
import com.aokur.bitcointicker.util.setCoinDetail
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding>() {

    override val TAG: String
        get() = CoinDetailFragment::class.java.simpleName

    private val viewModel: CoinDetailViewModel by viewModels()

    private val args by navArgs<CoinDetailFragmentArgs>()

    private var isFavourite: Boolean = false

    private lateinit var coinID: String

    @Inject
    lateinit var prefManager: PrefManager

    override fun getLayoutRes(): Int {
        return R.layout.fragment_coin_detail
    }

    override fun init(savedInstanceState: Bundle?) {
        coinID = args.coinId
        viewModel.isFavourite(coinID)

        viewModel.getCoinByID(coinID)
        viewModel.coinInfo.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> setCoinDetails(it.data!!)
            }
            binding.state = CoinDetailViewState(it.status)
        }

        viewModel.isFavouriteAdded.observe(viewLifecycleOwner) {
            if (it)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.favourite_add_success),
                    Toast.LENGTH_LONG
                ).show()
        }

        viewModel.isFavouriteDeleted.observe(viewLifecycleOwner) {
            if (it)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.favourite_delete_success),
                    Toast.LENGTH_LONG
                ).show()
        }

        viewModel.isFavourite.observe(viewLifecycleOwner) {
            if (it) {
                isFavourite = it
                binding.favoriteImageView.changeIconColor(isFavourite)
            }
        }

        prefManager.getRefreshInterval()?.let {
            binding.edtInterval.setText(prefManager.getRefreshInterval())
        }

        binding.apply {
            state = CoinDetailViewState(Status.LOADING)
            edtInterval.setText(prefManager.getRefreshInterval())

            edtInterval.setOnKeyListener(onKeyListener)
        }

        binding.favoriteImageView.setOnClickListener {
            changeFavoriteState(it)
        }
    }

    private fun changeFavoriteState(v: View) {
        if (isFavourite)
            viewModel.deleteFavourite(coinID)
        else
            viewModel.addToFavourites(viewModel.coinInfo.value?.data!!)

        isFavourite = !isFavourite
        (v as ImageView).changeIconColor(isFavourite)
    }

    private fun setCoinDetails(coinDetails: CoinDetailItem) {
        binding.coinDetail = setCoinDetail(coinDetails)
        (requireActivity() as MainActivity).supportActionBar?.apply {
            title = ""
        }
        Glide.with(requireContext()).load(coinDetails.image.large).into(binding.imgIconImage)
    }

    private fun setRefreshInterval() {
        val refreshInterval = binding.edtInterval.text.toString()
        repeatRequestByRefreshInterval(Integer.parseInt(refreshInterval))
        Toast.makeText(
            requireContext(),
            "All data and details will be refreshed every $refreshInterval seconds.",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun repeatRequestByRefreshInterval(refreshInterval: Int) {
        this.lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                viewModel.getCoinByID(coinID)
                prefManager.setRefreshInterval(refreshInterval)
                delay(refreshInterval.toLong() * 1000)
            }
        }
    }

    private val onKeyListener = object : View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_UP) {
                setRefreshInterval()
                return true
            }
            return false
        }
    }

    private infix fun ImageView.changeIconColor(isFavourite: Boolean) {
        val color = if (isFavourite) R.color.yellow else R.color.white

        this.colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(requireContext(), color),
            PorterDuff.Mode.SRC_IN
        )
    }
}