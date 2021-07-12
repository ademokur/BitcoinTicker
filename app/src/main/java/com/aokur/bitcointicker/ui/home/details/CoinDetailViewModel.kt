package com.aokur.bitcointicker.ui.home.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.data.model.CoinDetailItem
import com.aokur.bitcointicker.data.repository.CoinRepository
import com.aokur.bitcointicker.ui.base.BaseViewModel
import com.aokur.bitcointicker.util.Resource
import com.aokur.bitcointicker.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(private val coinRepository: CoinRepository): BaseViewModel() {

    private val _coinInfo = MutableLiveData<Resource<CoinDetailItem>>()
    val coinInfo: LiveData<Resource<CoinDetailItem>> = _coinInfo

    fun getCoinByID(id: String) = viewModelScope.launch {
        coinRepository.getCoinByID(id)
            .onStart {
                _result.value = Result(loading = R.string.loading)
            }
            .catch {
                Log.v("errorGetCoinByID", it.message.toString())
            }
            .collect {
                _coinInfo.value = it
            }
    }
}