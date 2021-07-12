package com.aokur.bitcointicker.ui.home.coin

import android.view.View
import com.aokur.bitcointicker.util.Status

class CoinViewState(private val status: Status) {
    fun getProgressBarVisibility() = if (status == Status.LOADING) View.VISIBLE else View.GONE
    fun getRecyclerViewVisibility() = if (status == Status.SUCCESS) View.VISIBLE else View.GONE
    fun getErrorMessageVisibility() = if (status == Status.ERROR) View.VISIBLE else View.GONE
}