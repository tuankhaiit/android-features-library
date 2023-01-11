package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.tuankhaiit.androidfeatureslibrary.R
import com.tuankhaiit.androidfeatureslibrary.presentation.base.adapter.BaseAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.model.SimpleItem

class SimpleAdapter : BaseAdapter<SimpleItem, SimpleViewHolder>(object : DiffUtil.ItemCallback<SimpleItem>() {
    override fun areItemsTheSame(oldItem: SimpleItem, newItem: SimpleItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SimpleItem, newItem: SimpleItem): Boolean {
        return oldItem == newItem
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