package com.aokur.bitcointicker.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoinMarketItem(
    @SerializedName("id")
    val coinId: String,

    @SerializedName("current_price")
    val currentPrice: Double,

    @SerializedName("high_24h")
    val highestPrice24h: Double,

    @SerializedName("image")
    val cryptoImage: String,

    @SerializedName("last_updated")
    val lastUpdated: String,

    @SerializedName("low_24h")
    val lowestPrice24h: Double,

    @SerializedName("name")
    val name: String,

    @SerializedName("price_change_24h")
    val priceChange24h: Double,

    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,

    @SerializedName("symbol")
    val symbol: String,

    ) : Parcelable