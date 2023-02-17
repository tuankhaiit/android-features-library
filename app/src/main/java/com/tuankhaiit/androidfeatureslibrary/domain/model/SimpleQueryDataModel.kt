package com.tuankhaiit.androidfeatureslibrary.domain.model

data class SimpleQueryDataModel(
    val text: String,
    val page: Int? = null,
    val pageSize: Int? = null
)