package com.gabrielmorenoibarra.offlinenotes.data

import android.content.SharedPreferences
import kotlin.reflect.KProperty

class DelegatedPreferences<T>(val prefs: SharedPreferences, private val defaultValue: T) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getPreference(property.name, defaultValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setPreference(property.name, value)
    }

    private fun getPreference(key: String, defaultValue: T): T {
        with(prefs) {
            val result: Any? = when (defaultValue) {
                is Boolean -> getBoolean(key, defaultValue)
                is Int -> getInt(key, defaultValue)
                is Long -> getLong(key, defaultValue)
                is Float -> getFloat(key, defaultValue)
                is String -> getString(key, defaultValue)
                else -> throw IllegalArgumentException()
            }
            @Suppress("UNCHECKED_CAST")
            return result as T
        }
    }

    private fun setPreference(key: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException()
            }.apply()
        }
    }
}
