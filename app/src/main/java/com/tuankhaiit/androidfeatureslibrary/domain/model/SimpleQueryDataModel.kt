package com.tuankhaiit.androidfeatureslibrary.domain.model

data class SimpleQueryDataModel(
    val text: String,
    val page: Int? = null,
    val pageSize: Int? = null
) {
    companion object {
        fun default() = SimpleQueryDataModel("android")
        fun query(text: String) = SimpleQueryDataModel(text)
    }
}