package com.tuankhaiit.androidfeatureslibrary.presentation.githubRepo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import com.tuankhaiit.androidfeatureslibrary.domain.repository.RepoRepository
import com.tuankhaiit.androidfeatureslibrary.operator.dropUntilTimeout
import com.tuankhaiit.androidfeatureslibrary.presentation.common.CommonItemUI
import com.tuankhaiit.androidfeatureslibrary.presentation.githubRepo.model.GithubRepoUiAction
import com.tuankhaiit.androidfeatureslibrary.presentation.githubRepo.model.GithubRepoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class GithubRepoViewModel @Inject constructor(
    private val repository: RepoRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"
        private val DEFAULT_QUERY: SimpleQueryDataModel = SimpleQueryDataModel.default()
    }

    /**
     * Stream of immutable states representative of the UI.
     */
    val state: StateFlow<GithubRepoUiState>

    val pagingDataFlow: Flow<PagingData<CommonItemUI>>

    /**
     * Processor of side effects from the UI which in turn feedback into [state]
     */
    val uiActions: (GithubRepoUiAction) -> Unit

    val openRepoEvent: Flow<RepoModel>

    init {
        val initialQuery: SimpleQueryDataModel =
            savedStateHandle[LAST_SEARCH_QUERY] ?: DEFAULT_QUERY
        val lastQueryScrolled: SimpleQueryDataModel =
            savedStateHandle[LAST_QUERY_SCROLLED] ?: DEFAULT_QUERY

        val actionFlow = MutableSharedFlow<GithubRepoUiAction>()

        openRepoEvent = actionFlow
            .filterIsInstance<GithubRepoUiAction.ItemClick>()
            .dropUntilTimeout(1000)
            .map { it.model }
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed()
            )

        val searchActionFlow = actionFlow
            .filterIsInstance<GithubRepoUiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(GithubRepoUiAction.Search(query = initialQuery)) }

        val scrollActionFlow = actionFlow
            .filterIsInstance<GithubRepoUiAction.Scroll>()
            .distinctUntilChanged()
            // This is shared to keep the flow "hot" while caching the last query scrolled,
            // otherwise each flatMapLatest invocation would lose the last query scrolled,
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(GithubRepoUiAction.Scroll(currentQuery = lastQueryScrolled)) }

        pagingDataFlow = searchActionFlow.flatMapLatest { searchData(it.query) }
            .map { pagingData ->
                pagingData.map {
                    CommonItemUI.Data(it)
                }
            }.map {
                it.insertSeparators { before, after ->
                    if (before == null) {
                        val data = after?.data?.roundedStarCount ?: 0
                        if (data >= 1) {
                            return@insertSeparators CommonItemUI.Header(
                                "${after?.data?.roundedStarCount}0.000+ stars"
                            )
                        } else {
                            return@insertSeparators CommonItemUI.Header(
                                "< 10.000+ stars"
                            )
                        }
                    } else if (after is CommonItemUI.Data<*>) {
                        if (before.data!!.roundedStarCount > after.data!!.roundedStarCount) {
                            if (after.data.roundedStarCount >= 1) {
                                return@insertSeparators CommonItemUI.Header(
                                    "${after.data.roundedStarCount}0.000+ stars"
                                )
                            } else {
                                return@insertSeparators CommonItemUI.Header(
                                    "< 10.000+ stars"
                                )
                            }
                        }
                    }
                    null
                }.insertSeparators { before, after ->
                    if (before is CommonItemUI.Data<*> && after is CommonItemUI.Data<*>) {
                        return@insertSeparators CommonItemUI.Divider
                    }
                    null
                }
            }
            .cachedIn(viewModelScope)

        state = combine(
            searchActionFlow,
            scrollActionFlow,
            ::Pair
        ).map { (search, scroll) ->
            GithubRepoUiState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                // If the search query matches the scroll query, the user has scrolled
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = GithubRepoUiState()
        )

        uiActions = { action ->
            viewModelScope.launch { actionFlow.emit(action) }
        }
    }

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        savedStateHandle[LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
        super.onCleared()
    }

    private fun searchData(queryDataModel: SimpleQueryDataModel): Flow<PagingData<RepoModel>> {
        return repository.getSearchResultStream(queryDataModel)
    }

}