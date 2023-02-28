package com.tuankhaiit.androidfeatureslibrary.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleQueryDataModel(
    val text: String,
    val page: Int? = null,
    val pageSize: Int? = null
) : Parcelable {
    companion object {
        fun default() = SimpleQueryDataModel("android")
        fun query(text: String) = SimpleQueryDataModel(text)
    }
}