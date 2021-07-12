package com.aokur.bitcointicker.ui.auth.register

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.aokur.bitcointicker.ui.auth.EntryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : EntryViewModel() {

    fun insertUser(email: String, password: String) = viewModelScope.launch {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _resultEntry.postValue(true)
            }
            .addOnFailureListener { exception ->
                _resultEntry.postValue(false)
                Log.v("errorLogin", exception.toString())
            }
    }
}