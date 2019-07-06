package com.gabrielmorenoibarra.offlinenotes

import android.app.Application
import com.gabrielmorenoibarra.offlinenotes.data.AppPrefsManager

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppPrefsManager.init()
    }
}
