package soy.gabimoreno.offlinenotes.framework.extension

import com.google.gson.Gson
import org.json.JSONArray

fun Any.toJSONArray(): JSONArray {
    return JSONArray(Gson().toJson(this, Any::class.java))
}
