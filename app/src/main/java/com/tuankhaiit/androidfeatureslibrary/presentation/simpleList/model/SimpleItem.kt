package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleItem(
    val title: String? = null,
    val subTitle: String? = null,
) : Parcelable