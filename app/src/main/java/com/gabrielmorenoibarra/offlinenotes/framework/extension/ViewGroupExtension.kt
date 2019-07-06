package com.gabrielmorenoibarra.offlinenotes.framework.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ViewGroup.inflateCustom(context: Context, @LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, true)
}
