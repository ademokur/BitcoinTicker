package com.aokur.bitcointicker.util

import com.aokur.bitcointicker.data.db.entity.CoinMarketEntity
import com.aokur.bitcointicker.data.model.CoinDetailItem
import com.aokur.bitcointicker.data.model.CoinMarketItem

fun String.formatUpdatedTime(): String {
    val year = substring(0, 4)
    val month = substring(5, 7)
    val day = substring(8, 10)
    val hour = substring(11, 13)
    val minute = substring(14, 16)
    val second = substring(17, 19)
    return "$day-$month-$year, ${Integer.parseInt(hour) + 3}:$minute:$second"
}

fun Number.formatPriceChange(): Double {
    return String.format("%.2f", this).replace(",", ".").toDouble()
}

fun setCoinDetail(coinDetails: CoinDetailItem): CoinDetailItem {
    val lastUpdated = coinDetails.lastUpdated.formatUpdatedTime()
    val priceChange24h = coinDetails.marketData.priceChange24h.formatPriceChange()
    val priceChangePercentage24h =
        coinDetails.marketData.priceChangePercentage24h.formatPriceChange()
    val hashingAlgorithm = coinDetails.hashingAlgorithm ?: "-"

    val marketData = coinDetails.marketData.copy(
        priceChange24h = priceChange24h,
        priceChangePercentage24h = priceChangePercentage24h
    )

    val copiedDetail = coinDetails.copy(
        lastUpdated = lastUpdated,
        marketData = marketData,
        hashingAlgorithm = hashingAlgorithm
    )

    return copiedDetail

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

fun getCoinMarketItems(coinMarketItemList: List<CoinMarketEntity>): MutableList<CoinMarketItem> {
    val databaseList = mutableListOf<CoinMarketItem>()
    coinMarketItemList.forEach {
        val coinMarketEntity = CoinMarketItem(
            it.cryptoID,
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