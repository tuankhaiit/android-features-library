package com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.tuankhaiit.androidfeatureslibrary.presentation.base.adapter.BasePagingAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.common.CommonItemUI

abstract class CommonUIStateAdapter<D, VH : CommonUIStateViewHolder> :
    BasePagingAdapter<CommonItemUI, CommonUIStateViewHolder>(object :
        DiffUtil.ItemCallback<CommonItemUI>() {
        override fun areItemsTheSame(oldItem: CommonItemUI, newItem: CommonItemUI): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CommonItemUI, newItem: CommonItemUI): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CommonItemUI.Data<*> -> DATA_TYPE
            is CommonItemUI.Header-> HEADER_TYPE
            is CommonItemUI.Divider -> DIVIDER_TYPE
            null -> throw UnsupportedOperationException("Unknown view")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonUIStateViewHolder {
        return when (viewType) {
            DATA_TYPE -> onCreateDataViewHolder(parent)
            HEADER_TYPE -> CommonHeaderViewHolder.create(parent)
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
        protected const val HEADER_TYPE: Int = 1
        protected const val DIVIDER_TYPE: Int = 2
    }
}