package com.aokur.bitcointicker.util

object Constants {

    const val BASE_COLLECTION_NAME = "Cryptocurrency"
    const val DETAIL_COLLECTION_NAME = "listFavouriteCrypto"
    const val REFRESH_INTERVAL: String = "refresh_interval"
    const val PREF_NAME: String = "coin_prefs"
    const val DB_NAME: String = "coin.db"
    const val PASSWORD_PATTERN: String = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$"
    const val INVALID_PASSWORD =
        "Password must contain uppercase letters, lowercase letters, numbers and must be at least 6 digits."
    const val INVALID_EMAIL = "Email format does not match."

}