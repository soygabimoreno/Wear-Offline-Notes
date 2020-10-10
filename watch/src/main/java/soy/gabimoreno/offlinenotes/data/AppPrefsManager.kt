package soy.gabimoreno.offlinenotes.data

import org.json.JSONArray
import soy.gabimoreno.offlinenotes.domain.Note
import soy.gabimoreno.offlinenotes.framework.extension.toJSONArray

object AppPrefsManager {

    private var list = mutableListOf<String>()

    fun init() {
        list = getListFromSharedPreferences().toMutableList()
    }

    fun getAllNotes(): List<String> {
        return list
    }

    fun getNote(position: Int): String {
        if (isValidPosition(position)) {
            return list[position]
        } else {
            throwIndexOutOfBoundsException(position)
        }
    }

    fun setNote(s: String, position: Int) {
        if (isValidPosition(position)) {
            list[position] = s
            saveListToSharedPreferences()
        } else {
            throwIndexOutOfBoundsException(position)
        }
    }

    fun addNote(s: String) {
        list.add(s)
        saveListToSharedPreferences()
    }

    fun deleteNote(position: Int) {
        list.removeAt(position)
        saveListToSharedPreferences()
    }

    fun deleteAllNotes() {
        list = mutableListOf()
        saveListToSharedPreferences()
    }

    private fun getListFromSharedPreferences(): List<String> {
        val prefs = AppPrefs.instance
        val text = prefs.text
        if (text.isNotBlank()) {
            val notes = JSONArray(text)
            val list = mutableListOf<String>()
            for (i in 0 until notes.length()) {
                val item = notes.getString(i)
                list.add(item)
            }
            return list
        } else {
            return emptyList()
        }
    }

    private fun saveListToSharedPreferences() {
        val prefs = AppPrefs.instance
        prefs.text = list.toJSONArray().toString()
    }

    private fun throwIndexOutOfBoundsException(position: Int): Nothing {
        throw IndexOutOfBoundsException("There is no $position position on list with ${Note.MAX_NOTES} positions")
    }

    private fun isValidPosition(position: Int) = position < list.size
    fun isNotEmpty() = list.isNotEmpty()
}
