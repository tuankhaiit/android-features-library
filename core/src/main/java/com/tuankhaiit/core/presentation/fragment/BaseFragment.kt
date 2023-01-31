package com.tuankhaiit.core.presentation.fragment

import androidx.fragment.app.Fragment
import com.tuankhaiit.core.presentation.activity.BaseActivity

abstract class BaseFragment : Fragment() {
    fun requireBaseActivity() : BaseActivity {
        return requireActivity() as BaseActivity
    }
}