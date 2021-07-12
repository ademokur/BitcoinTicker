package com.aokur.bitcointicker.ui.home.coin

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.databinding.FragmentCoinBinding
import com.aokur.bitcointicker.ui.base.BaseFragment
import com.aokur.bitcointicker.ui.home.MainActivity
import com.aokur.bitcointicker.util.NetworkController
import com.aokur.bitcointicker.util.PrefManager
import com.aokur.bitcointicker.util.Status
import com.aokur.bitcointicker.util.getCoinMarketItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CoinFragment: BaseFragment<FragmentCoinBinding>() {

    override val TAG: String
        get() = CoinFragment::class.java.simpleName

    val viewModel: CoinViewModel by viewModels()

    private var coinAdapter = CoinAdapter()

    @Inject
    lateinit var prefManager: PrefManager

    private var isSearchRunning: Boolean = false

    private val networkController: NetworkController by lazy {
        NetworkController(requireContext()).apply {
            startNetworkCallback()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_coin
    }

    override fun init(savedInstanceState: Bundle?) {
        val sessionState = viewModel.checkIfUserLoggedIn()
        if (sessionState) {

            setHasOptionsMenu(true)

            (requireActivity() as MainActivity).supportActionBar?.apply {
                title = getString(R.string.app_name)
            }

            binding.state = CoinViewState(Status.LOADING)

            binding.swipeRefreshLayout.setOnRefreshListener {
                binding.swipeRefreshLayout.isRefreshing = true
                viewModel.getAllCoins()
            }

            binding.coinRecyclerView.adapter = coinAdapter
            coinAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    binding.coinRecyclerView.smoothScrollToPosition(positionStart)
                }
            })

            coinAdapter.setOnItemClickListener {
                findNavController().navigate(CoinFragmentDirections.actionCoinFragmentToCoinDetailFragment(it.coinId))
            }

            viewModel.allCoins.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        coinAdapter.submitList(it.data)
                        viewModel.insertAllCoins(it.data!!)
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }
                binding.state = CoinViewState(it.status)
            }

            viewModel.coinByParameter.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        coinAdapter.submitList(getCoinMarketItems(it.data!!))
                        coinAdapter.notifyDataSetChanged()
                    }
                }
            }

            prefManager.getRefreshInterval()?.let {
                this.lifecycleScope.launch(Dispatchers.IO) {
                    while (true) {
                        viewModel.getAllCoins()
                        delay(Integer.parseInt(it).toLong() * 1000)
                    }
                }
            }

            networkController.isNetworkConnected.observe(viewLifecycleOwner) { internetConnected ->
                if (internetConnected) {
                    if (viewModel.allCoins.value?.data == null)
                        viewModel.getAllCoins()
                    else {
                        binding.state = CoinViewState(Status.SUCCESS)
                    }
                }
                else binding.state = CoinViewState(Status.ERROR)
            }
        }
        else {
            findNavController().navigate(CoinFragmentDirections.actionCoinFragmentToLoginFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_coin_list, menu)

        val searchItem = menu.findItem(R.id.action_search)

        val searchView = searchItem?.actionView as SearchView

        searchView.apply {
            queryHint = getString(R.string.coin_search)
            setOnQueryTextListener(onQueryTextListener)
        }

        searchView.setOnCloseListener {
            isSearchRunning = false
            viewModel.getAllCoins()
            false
        }

        return super.onCreateOptionsMenu(menu, inflater)
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText.isNullOrEmpty().not()) {
                isSearchRunning = true
                viewModel.getCoinsByParameter(newText!!)
            }
            else {
                isSearchRunning = false
                viewModel.getAllCoins()
            }

            return true
        }
    }
}