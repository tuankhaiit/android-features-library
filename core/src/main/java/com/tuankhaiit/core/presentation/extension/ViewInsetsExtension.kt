package com.tuankhaiit.core.presentation.extension

import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.*

fun View.paddingToFitBottomNavigation() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        view.updatePadding(bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom)
        insets
    }
}

fun View.marginToFitStatusBar() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val topMargin = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
        when (val lp = view.layoutParams) {
            is MarginLayoutParams -> lp.updateMargins(top = topMargin)
        }
        insets
    }
}