package com.aokur.bitcointicker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aokur.bitcointicker.data.db.entity.CoinMarketEntity

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCrypto(listCrypto: List<CoinMarketEntity>)

    @Query("SELECT * FROM table_coin WHERE name LIKE :parameter OR symbol LIKE :parameter")
    suspend fun getCryptoByParameter(parameter: String): List<CoinMarketEntity>
}