package com.aokur.bitcointicker.ui.home.coin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.data.db.entity.CoinMarketEntity
import com.aokur.bitcointicker.data.model.CoinMarketItem
import com.aokur.bitcointicker.data.repository.CoinRepository
import com.aokur.bitcointicker.ui.base.BaseViewModel
import com.aokur.bitcointicker.util.Resource
import com.aokur.bitcointicker.util.Result
import com.aokur.bitcointicker.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(private val coinRepository: CoinRepository) : BaseViewModel() {

    private val _allCoins = MutableLiveData<Resource<List<CoinMarketItem>>>()
    val allCoins: LiveData<Resource<List<CoinMarketItem>>> = _allCoins

    fun getAllCoins() = viewModelScope.launch {
        coinRepository.getAllCoins()
            .onStart {
                _result.value = Result(loading = R.string.loading)
            }
            .catch {
                Log.v("errorGetAllCoins", it.message.toString())
            }
            .collect {
                _allCoins.value = it
            }
    }

    fun insertAllCoins(listCrypto: List<CoinMarketItem>) = viewModelScope.launch {
        val coinEntityList = getCoinMarketEntity(listCrypto)

        coinRepository.insertAllCoins(coinEntityList)
            .onStart {
                _result.value = Result(loading = R.string.coin_loading)
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            Result(success = R.string.coin_success)
                        }
                    }
                    Status.ERROR -> Result(success = R.string.coin_error)
                }
            }
    }
}

fun getCoinMarketEntity(coinMarketItemList: List<CoinMarketItem>): MutableList<CoinMarketEntity> {
    val databaseList = mutableListOf<CoinMarketEntity>()
    coinMarketItemList.forEach {
        val coinMarketEntity = CoinMarketEntity(
            it.coinId,
            it.currentPrice,
            it.highestPrice24h,
            it.cryptoImage,
            it.lastUpdated,
            it.lowestPrice24h,
            it.name,
            it.priceChange24h,
            it.priceChangePercentage24h,
            it.symbol
        )
        databaseList.add(coinMarketEntity)
    }
    return databaseList
}