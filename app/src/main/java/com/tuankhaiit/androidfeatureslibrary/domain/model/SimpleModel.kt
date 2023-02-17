package com.tuankhaiit.androidfeatureslibrary.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleModel(
    val id: String,
    val title: String,
    val description: String,
    val color: Int
) : Parcelable