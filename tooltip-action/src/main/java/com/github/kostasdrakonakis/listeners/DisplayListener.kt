package com.github.kostasdrakonakis.listeners

import android.view.View
import com.github.kostasdrakonakis.ActionTooltip

interface DisplayListener {
    fun onViewDisplayed(view: View)

    fun onViewHidden(view: View)
}

inline fun ActionTooltip.displayListener(code: (view: View, isDisplayed: Boolean) -> Unit) {

}