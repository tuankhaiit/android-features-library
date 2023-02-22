package com.tuankhaiit.androidfeatureslibrary.presentation.githubRepo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuankhaiit.androidfeatureslibrary.databinding.ItemGithubRepoLayoutBinding
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.presentation.common.CommonItemUI
import com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter.CommonUIStateAdapter

class RepoAdapter(private val onClickListener: OnRepoItemClickListener) : CommonUIStateAdapter<RepoModel, RepoViewHolder>() {
    override fun onCreateDataViewHolder(parent: ViewGroup): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGithubRepoLayoutBinding.inflate(inflater, parent, false)
        return RepoViewHolder(binding) {
            val item = getItem(it) as? CommonItemUI.Data<*> ?: return@RepoViewHolder
            val model = item.data as? RepoModel ?: return@RepoViewHolder
            onClickListener.onRepoItemClick(binding.root, model)
        }
    }
}

interface OnRepoItemClickListener {
    fun onRepoItemClick(view: View, item: RepoModel)
}