package com.aokur.bitcointicker.util

import android.content.Context
import javax.inject.Inject

class PrefManager @Inject constructor(context: Context) {

    private var sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, 0)
    private val editor = sharedPreferences.edit()

    fun setRefreshInterval(duration: Int) {
        editor.apply {
            putString(Constants.REFRESH_INTERVAL, duration.toString())
            commit()
        }
    }

    fun getRefreshInterval(): String? {
        return sharedPreferences.getString(Constants.REFRESH_INTERVAL, null)
    }
}