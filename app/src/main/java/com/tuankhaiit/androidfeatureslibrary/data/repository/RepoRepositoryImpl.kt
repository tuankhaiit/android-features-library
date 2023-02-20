package com.tuankhaiit.androidfeatureslibrary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.paging.RepoPagingSource
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.service.GithubService
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import com.tuankhaiit.androidfeatureslibrary.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow

class RepoRepositoryImpl(private val apiService: GithubService) : RepoRepository {
    override fun getSearchResultStream(query: SimpleQueryDataModel): Flow<PagingData<RepoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { RepoPagingSource(apiService, query) }
        ).flow
    }
}