package com.aokur.bitcointicker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aokur.bitcointicker.data.db.dao.CoinDao
import com.aokur.bitcointicker.data.db.entity.CoinMarketEntity

@Database(
    entities = [CoinMarketEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}