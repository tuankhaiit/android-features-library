package com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.tuankhaiit.androidfeatureslibrary.presentation.base.adapter.BasePagingAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.common.CommonUI

abstract class CommonUIStateAdapter<D, VH : CommonUIStateViewHolder> :
    BasePagingAdapter<CommonUI, CommonUIStateViewHolder>(object :
        DiffUtil.ItemCallback<CommonUI>() {
        override fun areItemsTheSame(oldItem: CommonUI, newItem: CommonUI): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CommonUI, newItem: CommonUI): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CommonUI.Data<*> -> DATA_TYPE
            is CommonUI.Divider -> DIVIDER_TYPE
            null -> throw UnsupportedOperationException("Unknown view")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonUIStateViewHolder {
        return when (viewType) {
            DATA_TYPE -> onCreateDataViewHolder(parent)
            else -> CommonDividerViewHolder.create(parent)
        }
    }

    abstract fun onCreateDataViewHolder(parent: ViewGroup): VH

    override fun onBindViewHolder(holder: CommonUIStateViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        protected const val DATA_TYPE: Int = 0
        protected const val DIVIDER_TYPE: Int = 1
    }
}