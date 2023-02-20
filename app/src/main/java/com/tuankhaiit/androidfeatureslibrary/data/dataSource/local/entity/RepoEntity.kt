package com.tuankhaiit.androidfeatureslibrary.data.dataSource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("full_name") val fullName: String?,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("html_url") val url: String?,
    @ColumnInfo("stargazers_count") val stars: Int?,
    @ColumnInfo("forks_count") val forks: Int?,
    @ColumnInfo("language") val language: String?
)
