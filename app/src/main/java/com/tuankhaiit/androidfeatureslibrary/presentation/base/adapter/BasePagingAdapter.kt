package com.tuankhaiit.androidfeatureslibrary.presentation.base.adapter

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BasePagingAdapter<T : Any, VH : BaseViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, VH>(diffCallback) {
}