package com.gabrielmorenoibarra.offlinenotes.data

import android.content.Context
import android.content.SharedPreferences
import com.gabrielmorenoibarra.offlinenotes.App

class AppPrefs private constructor() {

    companion object {
        val instance = AppPrefs()
    }

    val prefs: SharedPreferences by lazy {
        val context = App.instance
        context.getSharedPreferences(context.packageName + "." + AppPrefs::class.java.simpleName, Context.MODE_PRIVATE)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    var text: String by DelegatedPreferences(prefs, "")
}
