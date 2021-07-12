package com.aokur.bitcointicker.data.remote

import com.aokur.bitcointicker.data.model.CoinDetailItem
import com.aokur.bitcointicker.data.model.CoinMarketItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * For more details go to -> https://www.coingecko.com/en/api
 * */
interface CoinApiService {

    /**
     * List all supported coins price, market cap, volume, and market related data
     * {vs_currency} The target currency of market data (usd, eur, jpy, etc.)
     * */
    @GET("coins/markets?vs_currency=usd")
    suspend fun getAllCoins(): Response<List<CoinMarketItem>>


    /**
     * Get current data (name, price, market, ... including exchange tickers) for a coin.
     * */
    @GET("coins/{id}")
    suspend fun getCoinByID(@Path("id") id: String): Response<CoinDetailItem>
}