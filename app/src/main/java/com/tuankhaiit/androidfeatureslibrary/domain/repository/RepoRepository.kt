package com.tuankhaiit.androidfeatureslibrary.domain.repository

import androidx.paging.PagingData
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    fun getSearchResultStream(query: SimpleQueryDataModel) : Flow<PagingData<RepoModel>>
}