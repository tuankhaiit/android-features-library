package com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote

import com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.dto.SimpleDTO
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.dto.SimplesResponseDTO
import com.tuankhaiit.androidfeatureslibrary.domain.common.DataState
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class SimpleDataSource @Inject constructor() : ServiceRemoteSource() {
    suspend fun getSimpleData(body: SimpleQueryDataModel): DataState<SimplesResponseDTO> {
        return try {
            delay(1000)

            if (Random.nextInt(0, 3) == 2) throw NullPointerException("Occurred error!!")

            val response = SimplesResponseDTO(1, data = (0..(body.pageSize ?: 10)).map {
                SimpleDTO("$it", "The number ${body.page} - $it", "Android Feature library")
            })
            DataState.Result(response)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }
}