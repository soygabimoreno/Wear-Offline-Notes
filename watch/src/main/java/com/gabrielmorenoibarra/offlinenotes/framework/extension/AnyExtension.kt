package com.gabrielmorenoibarra.offlinenotes.framework.extension

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.json.JSONArray
import org.json.JSONObject

fun Any.toJSONObject(): JSONObject {
    return JSONObject(Gson().toJson(this, Any::class.java))
}
fun Any.toJSONString(): String {
    return Gson().toJson(this, Any::class.java)
}

fun Any.toJSONArray(): JSONArray {
    return JSONArray(Gson().toJson(this, Any::class.java))
}
