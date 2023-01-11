package com.tuankhaiit.androidfeatureslibrary.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        translateStatusBarAndBottomNavigationBar()
    }

    private fun translateStatusBarAndBottomNavigationBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}