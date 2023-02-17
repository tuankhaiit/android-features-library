package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.tuankhaiit.androidfeatureslibrary.R
import com.tuankhaiit.androidfeatureslibrary.presentation.base.adapter.BaseAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.base.adapter.BasePagingAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.model.SimpleUI

class SimpleAdapter : BasePagingAdapter<SimpleUI, SimpleViewHolder>(object : DiffUtil.ItemCallback<SimpleUI>() {
    override fun areItemsTheSame(oldItem: SimpleUI, newItem: SimpleUI): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: SimpleUI, newItem: SimpleUI): Boolean {
        return false
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_simple_layout, parent, false)
        return SimpleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}