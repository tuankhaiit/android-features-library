package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.model

import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleModel

data class SimpleUI(
    val title: String? = null,
    val subTitle: String? = null,
    val color: Int? = null
) {
    companion object {
        fun from(model: SimpleModel): SimpleUI {
            return SimpleUI(
                title = model.title,
                subTitle = model.description,
                color = model.color
            )
        }
    }
}