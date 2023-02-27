package com.tuankhaiit.androidfeatureslibrary.data.dataSource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.service.GithubService
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import kotlinx.coroutines.delay

class RepoPagingSource(
    private val apiService: GithubService,
    private val query: SimpleQueryDataModel
) : PagingSource<Int, RepoModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoModel> {
        return try {
            val page = params.key ?: 1
            val response = apiService.searchRepos(query.text, page, query.pageSize ?: 10)
            val nextKey = if (response.items.isEmpty()) null else page + 1
            LoadResult.Page(
                data = response.items.map { it.toModel() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey?.takeIf { it < 3 }
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, RepoModel>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
        return null
    }

}