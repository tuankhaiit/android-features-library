package com.tuankhaiit.androidfeatureslibrary.data.dataSource.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel

@OptIn(ExperimentalPagingApi::class)
class RepoMediator : RemoteMediator<Int, RepoModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoModel>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

}