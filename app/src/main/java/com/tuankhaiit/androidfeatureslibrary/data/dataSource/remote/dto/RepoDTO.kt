package com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.dto

import com.google.gson.annotations.SerializedName
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import kotlin.random.Random

data class RepoDTO(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("html_url")
    val url: String?,
    @SerializedName("stargazers_count")
    val stars: Int?,
    @SerializedName("forks_count")
    val forks: Int?,
    @SerializedName("language")
    val language: String?
) {
    fun toModel(): RepoModel {
        return RepoModel(
            id = id ?: Random.nextLong(10000L),
            name = name ?: "",
            fullName = fullName ?: "",
            description = description ?: "",
            url = url ?: "",
            stars = stars ?: 0,
            forks = forks ?: 0,
            language = language ?: ""
        )
    }
}