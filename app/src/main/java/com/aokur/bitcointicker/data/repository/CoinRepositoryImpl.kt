package com.aokur.bitcointicker.data.repository

import com.aokur.bitcointicker.data.db.dao.CoinDao
import com.aokur.bitcointicker.data.db.entity.CoinMarketEntity
import com.aokur.bitcointicker.data.model.CoinDetailItem
import com.aokur.bitcointicker.data.model.CoinMarketItem
import com.aokur.bitcointicker.data.remote.CoinApiService
import com.aokur.bitcointicker.util.Resource
import com.aokur.bitcointicker.util.getResourceByDatabaseRequest
import com.aokur.bitcointicker.util.getResourceByNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApiService: CoinApiService,
    private val coinDao: CoinDao
) : CoinRepository {

    override suspend fun getAllCoins(): Flow<Resource<List<CoinMarketItem>>> = flow {
        emit(getResourceByNetworkRequest { coinApiService.getAllCoins() })
    }

    override suspend fun getCoinByID(id: String): Flow<Resource<CoinDetailItem>> = flow {
        emit(getResourceByNetworkRequest { coinApiService.getCoinByID(id) })
    }

    override suspend fun insertAllCoins(listCrypto: List<CoinMarketEntity>): Flow<Resource<Unit>> =
        flow {
            emit(getResourceByDatabaseRequest { coinDao.insertAllCrypto(listCrypto) })
        }

    override suspend fun getCoinsByParameter(parameter: String): Flow<Resource<List<CoinMarketEntity>>> =
        flow {
            emit(getResourceByDatabaseRequest { coinDao.getCryptoByParameter(parameter) })
        }
}