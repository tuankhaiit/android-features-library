package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import com.tuankhaiit.androidfeatureslibrary.domain.repository.SimpleRepository
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.model.SimpleUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SimpleListViewModel @Inject constructor(private val repository: SimpleRepository) :
    ViewModel() {
    val searches = MutableSharedFlow<SimpleQueryDataModel>()
        .onStart { emit(SimpleQueryDataModel(text = "")) }

    val pagingDataFlow = searches
        .flatMapLatest { searchData(it) }
        .map { pagingData ->
            pagingData.map {
                SimpleUI.from(it)
            }
        }
        .cachedIn(viewModelScope)

    private fun searchData(queryDataModel: SimpleQueryDataModel): Flow<PagingData<SimpleModel>> {
        return repository.getSearchResultStream(queryDataModel)
    }

}