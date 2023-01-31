package com.tuankhaiit.androidfeatureslibrary.presentation

import android.os.Bundle
import com.tuankhaiiit.androidfeatureslibrary.simplelist.SimpleListFragment
import com.tuankhaiit.androidfeatureslibrary.R
import com.tuankhaiit.androidfeatureslibrary.simple_bottom_menu.SimpleBottomNavigationFragment
import com.tuankhaiit.core.presentation.activity.BaseActivity

class RootActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.rootContainer, SimpleBottomNavigationFragment.newInstance())
            commit()
        }
    }
}