package com.aokur.bitcointicker.ui.auth

data class EntryFormState(
    var passwordError: String? = null,
    var emailError: String? = null,
    val isDataValid: Boolean = false
)