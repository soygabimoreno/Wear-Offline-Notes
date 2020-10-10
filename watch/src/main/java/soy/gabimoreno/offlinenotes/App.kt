package soy.gabimoreno.offlinenotes

import android.app.Application
import soy.gabimoreno.offlinenotes.data.AppPrefsManager

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
