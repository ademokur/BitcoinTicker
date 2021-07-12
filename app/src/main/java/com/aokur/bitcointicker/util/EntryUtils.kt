package com.aokur.bitcointicker.util

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import com.aokur.bitcointicker.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.verifyEmail() {
    val title = getString(R.string.warning)
    val message = getString(R.string.check_email_address)
    val check = getString(R.string.check)
    val discard = getString(R.string.discard)
    val packageName = getString(R.string.gmail_package_name)

    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton(check) { dialogInterface: DialogInterface, i: Int ->
            val intent = packageManager.getLaunchIntentForPackage(packageName)
            openGmail(intent)
        }

        .setNegativeButton(discard) { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        .show()
}

fun Context.openGmail(intent: Intent?) {
    if (intent != null)
        startActivity(intent)
    else
        Toast.makeText(this, getString(R.string.error_install_gmail), Toast.LENGTH_LONG).show()
}