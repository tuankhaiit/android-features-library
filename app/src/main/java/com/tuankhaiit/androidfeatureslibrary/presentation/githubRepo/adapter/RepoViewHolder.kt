package com.tuankhaiit.androidfeatureslibrary.presentation.githubRepo.adapter

import com.tuankhaiit.androidfeatureslibrary.databinding.ItemGithubRepoLayoutBinding
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.presentation.common.CommonItemUI
import com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter.CommonUIStateViewHolder

class RepoViewHolder(private val binding: ItemGithubRepoLayoutBinding, private val onClick: (position: Int) -> Unit) :
    CommonUIStateViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClick(bindingAdapterPosition)
        }
    }

    override fun bind(item: CommonItemUI?) {
        super.bind(item)
        if (item is CommonItemUI.Data<*>) {
            if (item.data is RepoModel) {
                item.data.let { data ->
                    with(binding) {
                        txtTitle.text = data.name
                        txtSubTitle.text = data.description
                        txtStarNumber.text = data.stars.toString()
                        txtForkNumber.text = data.forks.toString()
                    }
                }
            }
        }
    }
}