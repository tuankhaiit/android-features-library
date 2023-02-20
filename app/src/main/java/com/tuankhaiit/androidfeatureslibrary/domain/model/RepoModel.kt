package com.tuankhaiit.androidfeatureslibrary.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RepoModel(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String,
    val url: String,
    val stars: Int,
    val forks: Int,
    val language: String
) : Parcelable