package com.tuankhaiit.androidfeatureslibrary.presentation.extension

import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.updateLayoutParams

fun View.paddingFitStatusBar() {
    setOnApplyWindowInsetsListener { _, insets ->
        val statusBarHeight = insets.getInsets(WindowInsets.Type.statusBars()).top
        setPadding(paddingLeft, statusBarHeight, paddingRight, paddingBottom)
        if (this is ViewGroup) {
            clipToPadding = false
        }
        return@setOnApplyWindowInsetsListener insets
    }
}

fun View.marginFitStatusBar() {
    setOnApplyWindowInsetsListener { _, insets ->
        val statusBarHeight = insets.getInsets(WindowInsets.Type.statusBars()).top
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = statusBarHeight
        }
        return@setOnApplyWindowInsetsListener insets
    }
}

fun View.paddingFitBottomNavigationBar() {
    setOnApplyWindowInsetsListener { _, insets ->
        val navigationBarHeight =
            insets.getInsets(WindowInsets.Type.navigationBars()).bottom
        setPadding(paddingLeft, paddingTop, paddingRight, navigationBarHeight)
        if (this is ViewGroup) {
            clipToPadding = false
            clipChildren = false
        }
        return@setOnApplyWindowInsetsListener insets
    }
}

fun View.marginFitBottomNavigationBar() {
    setOnApplyWindowInsetsListener { _, insets ->
        val navigationBarHeight = insets.getInsets(WindowInsets.Type.navigationBars()).bottom
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = navigationBarHeight
        }
        return@setOnApplyWindowInsetsListener insets
    }
}