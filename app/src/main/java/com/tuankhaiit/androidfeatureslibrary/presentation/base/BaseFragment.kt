package com.tuankhaiit.androidfeatureslibrary.presentation.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    fun requireBaseActivity() : BaseActivity? {
        return requireActivity() as BaseActivity?
    }
}