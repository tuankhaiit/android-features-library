package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.tuankhaiit.androidfeatureslibrary.R
import com.tuankhaiit.androidfeatureslibrary.presentation.base.adapter.BaseViewHolder
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.model.SimpleItem
import kotlin.random.Random

class SimpleViewHolder(itemView: View) : BaseViewHolder(itemView) {
    fun bind(item: SimpleItem) {
        itemView.apply {
            val backgroundColor = Color.argb(
                255,
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256)
            )
            setBackgroundColor(backgroundColor)
            findViewById<TextView>(R.id.txtTitle).text = item.title
            findViewById<TextView>(R.id.txtSubTitle).text = item.subTitle
        }
    }
}