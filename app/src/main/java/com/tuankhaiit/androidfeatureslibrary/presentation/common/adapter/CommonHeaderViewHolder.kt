package com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tuankhaiit.androidfeatureslibrary.databinding.ItemCommonHeaderBinding
import com.tuankhaiit.androidfeatureslibrary.presentation.common.CommonItemUI

class CommonHeaderViewHolder(private val binding: ItemCommonHeaderBinding) :
    CommonUIStateViewHolder(binding.root) {

    override fun bind(item: CommonItemUI?) {
        if (item is CommonItemUI.Header) {
            binding.txtMessage.text = item.message
        }
    }

    companion object {
        fun create(parent: ViewGroup): CommonHeaderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCommonHeaderBinding.inflate(inflater, parent, false)
            return CommonHeaderViewHolder(binding)
        }
    }
}