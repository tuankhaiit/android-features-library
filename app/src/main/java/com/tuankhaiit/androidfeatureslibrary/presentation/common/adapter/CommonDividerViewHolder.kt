package com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tuankhaiit.androidfeatureslibrary.databinding.ItemCommonDividerBinding

class CommonDividerViewHolder(binding: ItemCommonDividerBinding) :
    CommonUIStateViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): CommonDividerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCommonDividerBinding.inflate(inflater, parent, false)
            return CommonDividerViewHolder(binding)
        }
    }

}