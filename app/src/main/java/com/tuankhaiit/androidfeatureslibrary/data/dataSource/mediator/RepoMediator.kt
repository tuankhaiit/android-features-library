package com.tuankhaiit.androidfeatureslibrary.data.dataSource.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.local.dao.RepoDao
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.local.database.RepoDatabase
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.service.GithubService
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel

@OptIn(ExperimentalPagingApi::class)
class RepoMediator(
    private val query: SimpleQueryDataModel,
    private val service: GithubService,
    private val repoDao: RepoDao
) : RemoteMediator<Int, RepoModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoModel>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

}