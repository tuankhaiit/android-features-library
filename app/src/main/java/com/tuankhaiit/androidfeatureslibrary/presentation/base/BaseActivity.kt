package com.tuankhaiit.androidfeatureslibrary.presentation.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        translateStatusBarAndBottomNavigationBar()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
    }
}

fun AppCompatActivity.translateStatusBarAndBottomNavigationBar() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}