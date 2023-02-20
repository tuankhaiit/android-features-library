package com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.dto

import com.google.gson.annotations.SerializedName

data class RepoSearchResponseDTO(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<RepoDTO> = emptyList(),
    val nextPage: Int? = null
)