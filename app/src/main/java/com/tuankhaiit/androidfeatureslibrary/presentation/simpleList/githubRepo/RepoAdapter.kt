package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.githubRepo

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tuankhaiit.androidfeatureslibrary.databinding.ItemGithubRepoLayoutBinding
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter.CommonUIStateAdapter

class RepoAdapter : CommonUIStateAdapter<RepoModel, RepoViewHolder>() {
    override fun onCreateDataViewHolder(parent: ViewGroup): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGithubRepoLayoutBinding.inflate(inflater, parent, false)
        return RepoViewHolder(binding)
    }
}