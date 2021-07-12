package com.aokur.bitcointicker.ui.home.details

import android.view.View
import com.aokur.bitcointicker.util.Status

class CoinDetailViewState(private val status: Status) {
    fun getProgressBarVisibility() = if (status == Status.LOADING) View.VISIBLE else View.GONE
    fun getViewVisibility() = if (status == Status.SUCCESS) View.VISIBLE else View.GONE
}