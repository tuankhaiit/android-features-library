package com.tuankhaiit.androidfeatureslibrary.data.dataSource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.SimpleDataSource
import com.tuankhaiit.androidfeatureslibrary.domain.common.map
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel

class SimplePagingSource(
    private val dataSource: SimpleDataSource,
    private val queryDataModel: SimpleQueryDataModel
) : PagingSource<Int, SimpleModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimpleModel> {
        return try {
            val page = params.key ?: 1
            dataSource.getSimpleData(queryDataModel.copy(page = page, pageSize = 10))
                .map(
                    mapResult = { data ->
                        LoadResult.Page(
                            data = (data?.data ?: emptyList()).map { it.toSimpleModel() },
                            prevKey = (page - 1).let { if (it < 1) null else it },
                            nextKey = page + 1
                        )
                    },
                    mapError = { throwable ->
                        LoadResult.Error(throwable)
                    }
                )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, SimpleModel>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}