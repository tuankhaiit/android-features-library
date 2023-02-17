package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.adapter

import android.view.View
import android.widget.TextView
import com.tuankhaiit.androidfeatureslibrary.R
import com.tuankhaiit.androidfeatureslibrary.presentation.base.adapter.BaseViewHolder
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.model.SimpleUI

class SimpleViewHolder(itemView: View) : BaseViewHolder(itemView) {
    fun bind(item: SimpleUI?) {
        itemView.apply {
            item?.color?.run {
                setBackgroundColor(this)
            }
            findViewById<TextView>(R.id.txtTitle).text = item?.title
            findViewById<TextView>(R.id.txtSubTitle).text = item?.subTitle
        }
    }
}