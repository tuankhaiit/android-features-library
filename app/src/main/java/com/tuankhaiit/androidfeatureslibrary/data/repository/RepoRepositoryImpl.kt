package com.tuankhaiit.androidfeatureslibrary.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.local.database.RepoDatabase
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.mediator.RepoMediator
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.paging.RepoPagingSource
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.service.GithubService
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import com.tuankhaiit.androidfeatureslibrary.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow

class RepoRepositoryImpl(private val apiService: GithubService, private val database: RepoDatabase) :
    RepoRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getSearchResultStream(query: SimpleQueryDataModel): Flow<PagingData<RepoModel>> {
        val dbQuery = "%${query.text.replace(' ', '%')}%"
        val pagingSourceFactory = { database.reposDao().reposByName(dbQuery) }
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
//            remoteMediator = RepoMediator(query, apiService, database),
            pagingSourceFactory = { RepoPagingSource(apiService, query) }
        ).flow
    }
}