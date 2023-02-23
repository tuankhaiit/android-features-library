package com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tuankhaiit.androidfeatureslibrary.databinding.ItemCommonHeaderBinding
import com.tuankhaiit.androidfeatureslibrary.presentation.base.adapter.BasePagingAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.common.CommonItemUI
import kotlinx.coroutines.NonDisposableHandle.parent

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

    fun isHeader(position: Int) = getItem(position) is CommonItemUI.Header

    fun getHeaderView(parent: RecyclerView, position: Int): View {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCommonHeaderBinding.inflate(inflater, parent, false)

        return binding.root
    }

    companion object {
        protected const val DATA_TYPE: Int = 0
        protected const val HEADER_TYPE: Int = 1
        protected const val DIVIDER_TYPE: Int = 2
    }
}