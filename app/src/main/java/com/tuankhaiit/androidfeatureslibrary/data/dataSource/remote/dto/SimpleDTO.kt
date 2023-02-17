package com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.dto

import android.graphics.Color
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleModel
import kotlin.random.Random

class SimpleDTO(
    val id: String?,
    val title: String?,
    val description: String?
) {
    fun toSimpleModel(): SimpleModel {
        return SimpleModel(
            id ?: "",
            title ?: "",
            description ?: "",
            color = Color.argb(
                255,
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256)
            )
        )
    }
}