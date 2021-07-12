package com.aokur.bitcointicker.data.repository

import com.aokur.bitcointicker.data.db.entity.CoinMarketEntity
import com.aokur.bitcointicker.data.model.CoinDetailItem
import com.aokur.bitcointicker.data.model.CoinMarketItem
import com.aokur.bitcointicker.util.Resource
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getAllCoins(): Flow<Resource<List<CoinMarketItem>>>
    suspend fun getCoinByID(id: String): Flow<Resource<CoinDetailItem>>
    suspend fun insertAllCoins(listCrypto: List<CoinMarketEntity>): Flow<Resource<Unit>>
    suspend fun getCoinsByParameter(parameter: String): Flow<Resource<List<CoinMarketEntity>>>
}