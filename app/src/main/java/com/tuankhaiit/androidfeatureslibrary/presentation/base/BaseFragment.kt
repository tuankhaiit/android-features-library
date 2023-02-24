package com.tuankhaiit.androidfeatureslibrary.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.tuankhaiit.androidfeatureslibrary.R

abstract class BaseFragment : Fragment() {
    var enableFitSystemWindow: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (enableFitSystemWindow) {
            view.fitsSystemWindows = true
        }
    }

    protected open fun setupToolbar(rootView: View) {
        rootView.findViewById<Toolbar>(R.id.toolbar)?.let { toolbar: Toolbar ->
            (activity as AppCompatActivity?)?.apply {
                setSupportActionBar(toolbar)
                toolbar.setNavigationOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    protected open fun hideKeyboardAndClearFocus(view: View? = null) {
        hideKeyboard(view)
        (view ?: activity?.currentFocus)?.clearFocus()
    }

    protected open fun hideKeyboard(view: View? = null) {
        activity?.apply {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                hideSoftInputFromWindow(
                    (view ?: currentFocus)?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }
}