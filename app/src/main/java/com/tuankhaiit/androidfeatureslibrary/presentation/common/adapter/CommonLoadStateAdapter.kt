package com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class CommonLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<CommonLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: CommonLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CommonLoadStateViewHolder {
        return CommonLoadStateViewHolder.create(parent, retry)
    }

}