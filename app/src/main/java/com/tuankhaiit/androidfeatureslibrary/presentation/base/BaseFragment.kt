package com.tuankhaiit.androidfeatureslibrary.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    var enableFitSystemWindow: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (enableFitSystemWindow) {
            view.fitsSystemWindows = true
        }
    }
}