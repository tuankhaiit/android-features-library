package com.tuankhaiit.androidfeatureslibrary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.paging.SimplePagingSource
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.SimpleDataSource
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import com.tuankhaiit.androidfeatureslibrary.domain.repository.SimpleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SimpleRepositoryImpl @Inject constructor(private val dataSource: SimpleDataSource) :
    SimpleRepository {
    override fun getSearchResultStream(queryDataModel: SimpleQueryDataModel): Flow<PagingData<SimpleModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SimplePagingSource(dataSource, queryDataModel) }
        ).flow
    }
}