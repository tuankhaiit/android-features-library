package com.tuankhaiit.androidfeatureslibrary.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KeyValueModel(
    val key: String,
    val value: String
) : Parcelable