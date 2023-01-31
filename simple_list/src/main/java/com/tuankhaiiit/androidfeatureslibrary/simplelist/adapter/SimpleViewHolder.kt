package com.tuankhaiiit.androidfeatureslibrary.simplelist.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.tuankhaiiit.androidfeatureslibrary.simplelist.R
import com.tuankhaiiit.androidfeatureslibrary.simplelist.model.SimpleItem
import com.tuankhaiit.core.presentation.adapter.BaseViewHolder
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