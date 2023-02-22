package com.tuankhaiit.androidfeatureslibrary.presentation

import android.os.Bundle
import com.tuankhaiit.androidfeatureslibrary.R
import com.tuankhaiit.androidfeatureslibrary.presentation.base.BaseActivity
import com.tuankhaiit.androidfeatureslibrary.presentation.githubRepo.GithubRepoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.rootContainer, GithubRepoFragment.newInstance())
            commit()
        }
    }
}