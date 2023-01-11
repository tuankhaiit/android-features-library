package com.tuankhaiit.androidfeatureslibrary.presentation

import android.os.Bundle
import com.tuankhaiit.androidfeatureslibrary.R
import com.tuankhaiit.androidfeatureslibrary.presentation.base.BaseActivity
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.SimpleListFragment
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.model.SimpleItem

class RootActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        supportFragmentManager.beginTransaction().apply {
            val data = ArrayList<SimpleItem>()
            (1..30).forEach {
                data.add(SimpleItem("This is $it", "Android Features Library"))
            }
            replace(R.id.rootContainer, SimpleListFragment.newInstance(data))
            commit()
        }
    }
}