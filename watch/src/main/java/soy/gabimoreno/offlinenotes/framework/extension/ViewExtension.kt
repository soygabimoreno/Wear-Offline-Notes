package soy.gabimoreno.offlinenotes.framework.extension

import android.view.View
import android.view.ViewTreeObserver

inline fun <T : View> T.afterMeasured(crossinline listener: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                listener()
            }
        }
    })
}
