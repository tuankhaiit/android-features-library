package com.tuankhaiit.androidfeatureslibrary.domain.repository

import androidx.paging.PagingData
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import kotlinx.coroutines.flow.Flow

interface SimpleRepository {
    fun getSearchResultStream(queryDataModel: SimpleQueryDataModel): Flow<PagingData<SimpleModel>>
}