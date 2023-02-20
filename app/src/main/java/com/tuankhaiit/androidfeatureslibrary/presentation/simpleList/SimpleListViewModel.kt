package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import com.tuankhaiit.androidfeatureslibrary.domain.repository.RepoRepository
import com.tuankhaiit.androidfeatureslibrary.presentation.common.CommonUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SimpleListViewModel @Inject constructor(private val repository: RepoRepository) :
    ViewModel() {
    val searches = MutableSharedFlow<SimpleQueryDataModel>()
        .onStart { emit(SimpleQueryDataModel(text = "android")) }

    val pagingDataFlow: Flow<PagingData<CommonUI>> = searches
        .flatMapLatest { searchData(it) }
        .map { pagingData ->
            pagingData.map {
                CommonUI.Data(it)
            }
        }.map {
            it.insertSeparators { before, after ->
                if (before == null || after == null) {
                    // we're at the beginning or the end of the list
                    return@insertSeparators null
                }
                return@insertSeparators CommonUI.Divider
            }
        }
        .cachedIn(viewModelScope)

    private fun searchData(queryDataModel: SimpleQueryDataModel): Flow<PagingData<RepoModel>> {
        return repository.getSearchResultStream(queryDataModel)
    }

}