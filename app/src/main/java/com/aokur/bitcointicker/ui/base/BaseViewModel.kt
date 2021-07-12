package com.aokur.bitcointicker.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aokur.bitcointicker.util.Result
import com.google.firebase.auth.FirebaseAuth

open class BaseViewModel : ViewModel() {
    val _result = MutableLiveData<Result>()
    val result: LiveData<Result> = _result

    val firebaseAuth = FirebaseAuth.getInstance()

}