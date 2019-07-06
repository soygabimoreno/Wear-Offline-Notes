package com.gabrielmorenoibarra.offlinenotes.framework.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle

fun Intent?.parseExtras(resultCode: Int, listener: (Bundle) -> Unit) {
    if (resultCode == Activity.RESULT_OK) {
        this?.let {
            extras?.let {
                listener(it)
            }
        }
    }
}
