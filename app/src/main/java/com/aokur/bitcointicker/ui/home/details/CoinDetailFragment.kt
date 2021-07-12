package com.aokur.bitcointicker.ui.home.details

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
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
class CoinDetailFragment: BaseFragment<FragmentCoinDetailBinding>() {

    override val TAG: String
        get() = CoinDetailFragment::class.java.simpleName

    private val viewModel: CoinDetailViewModel by viewModels()

    private val args by navArgs<CoinDetailFragmentArgs>()

    private lateinit var coinID: String

    @Inject
    lateinit var prefManager: PrefManager

    override fun getLayoutRes(): Int {
        return R.layout.fragment_coin_detail
    }

    override fun init(savedInstanceState: Bundle?) {
        coinID = args.coinId

        viewModel.getCoinByID(coinID)
        viewModel.coinInfo.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> setCoinDetails(it.data!!)
            }
            binding.state = CoinDetailViewState(it.status)
        }

        prefManager.getRefreshInterval()?.let {
            binding.edtInterval.setText(prefManager.getRefreshInterval())
        }

        binding.apply {
            state = CoinDetailViewState(Status.LOADING)
            edtInterval.setText(prefManager.getRefreshInterval())

            edtInterval.setOnKeyListener(onKeyListener)
        }
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
        Toast.makeText(requireContext(), "All data and details will be refreshed every $refreshInterval seconds.", Toast.LENGTH_LONG).show()
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
}