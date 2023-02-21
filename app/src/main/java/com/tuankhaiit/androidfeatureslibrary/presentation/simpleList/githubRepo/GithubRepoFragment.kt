package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.githubRepo

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuankhaiit.androidfeatureslibrary.databinding.FragmentGithubRepoBinding
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel
import com.tuankhaiit.androidfeatureslibrary.presentation.base.BaseFragment
import com.tuankhaiit.androidfeatureslibrary.presentation.common.CommonItemUI
import com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter.CommonLoadStateAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.githubRepo.adapter.RepoAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.githubRepo.model.GithubRepoUiAction
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.githubRepo.model.GithubRepoUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GithubRepoFragment : BaseFragment() {

    companion object {
        fun newInstance(): GithubRepoFragment {
            return GithubRepoFragment()
        }
    }

    private lateinit var binding: FragmentGithubRepoBinding

    private val viewModel: GithubRepoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGithubRepoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindState(viewLifecycleOwner, viewModel.state, viewModel.uiActions)
    }

    private fun FragmentGithubRepoBinding.bindState(
        lifecycleOwner: LifecycleOwner,
        uiState: StateFlow<GithubRepoUiState>,
        uiActions: (GithubRepoUiAction) -> Unit
    ) {
        val repoAdapter = RepoAdapter()

        val refresh = CommonLoadStateAdapter { repoAdapter.retry() }
        val header = CommonLoadStateAdapter { repoAdapter.retry() }
        val footer = CommonLoadStateAdapter { repoAdapter.retry() }
        repoAdapter.addLoadStateListener { loadStates ->
            refresh.loadState = loadStates.refresh
            header.loadState = loadStates.prepend
            footer.loadState = loadStates.append
        }

        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ConcatAdapter(refresh, header, repoAdapter, footer)
        }

        refreshLayout.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repoAdapter.refresh()
                refreshLayout.isRefreshing = false
            }
        }

        bindSearch(lifecycleOwner, viewModel.state, uiActions)
        bindList(lifecycleOwner, repoAdapter, uiState, viewModel.pagingDataFlow, uiActions)
    }

    private fun FragmentGithubRepoBinding.searchRepoWithNewQuery(onQueryChanged: (GithubRepoUiAction.Search) -> Unit) {
        edtQuery.text.toString().trim().let {
            if (it.isNotBlank()) {
//                rvList.scrollToPosition(0)
                onQueryChanged(GithubRepoUiAction.Search(query = SimpleQueryDataModel.query(it)))
            }
        }
    }

    private fun FragmentGithubRepoBinding.bindSearch(
        lifecycleOwner: LifecycleOwner,
        uiState: StateFlow<GithubRepoUiState>,
        onQueryChanged: (GithubRepoUiAction.Search) -> Unit
    ) {
        edtQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                searchRepoWithNewQuery(onQueryChanged)
                true
            } else {
                false
            }
        }
        edtQuery.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchRepoWithNewQuery(onQueryChanged)
                true
            } else {
                false
            }
        }
        val textFlow = MutableSharedFlow<String>()
        edtQuery.doOnTextChanged { text, _, _, _ ->
            lifecycleOwner.lifecycleScope.launch {
                textFlow.emit(text.toString())
            }
        }
        lifecycleOwner.lifecycleScope.launch {
            textFlow.debounce(2000L).collect {
                searchRepoWithNewQuery(onQueryChanged)
            }
        }
        lifecycleOwner.lifecycleScope.launch {
            uiState.map {
                it.query
            }.distinctUntilChanged()
                .collect {
                    if (edtQuery.text.toString() != it.text) {
                        edtQuery.setText(it.text)
                        edtQuery.setSelection(it.text.length)
                    }
                }
        }
    }

    private fun FragmentGithubRepoBinding.bindList(
        lifecycleOwner: LifecycleOwner,
        adapter: RepoAdapter,
        uiState: StateFlow<GithubRepoUiState>,
        pagingDataFlow: Flow<PagingData<CommonItemUI>>,
        onScrollChanged: (GithubRepoUiAction.Scroll) -> Unit
    ) {
        lifecycleOwner.run {
            rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy != 0) onScrollChanged(GithubRepoUiAction.Scroll(currentQuery = uiState.value.query))
                }
            })

            val notLoading = adapter.loadStateFlow
                .distinctUntilChangedBy { it.source.refresh }
                .map { it.source.refresh is LoadState.NotLoading }

            val hasNotScrolledForCurrentSearch = uiState
                .map { it.hasNotScrolledForCurrentSearch }
                .distinctUntilChanged()

            val shouldScrollToTop = combine(
                notLoading,
                hasNotScrolledForCurrentSearch,
                Boolean::and
            ).distinctUntilChanged()

            lifecycleScope.launch {
                shouldScrollToTop.collect { shouldScroll ->
                    if (shouldScroll) rvList.scrollToPosition(0)
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    pagingDataFlow.collectLatest {
                        adapter.submitData(it)
                    }
                }
            }

            lifecycleScope.launch {
                adapter.loadStateFlow
                    .collect { loadState ->
                        // Show empty state
                        val isListEmpty =
                            loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                        layoutEmpty.isVisible = isListEmpty
                        rvList.isVisible = !isListEmpty

//                    // Show loading spinner during initial load or refresh
//                    progressBar.isVisible = loadState.source.refresh is LoadState.Loading
//                    // Show the retry state if initial load or refresh fails
//                    btnRetry.isVisible = loadState.source.refresh is LoadState.Error

                        // Check error state if exist then show toast
                        // regardless of whether it came from RemoteMediator or PagingSource
                        val statesNeedToCheck = arrayListOf(
                            loadState.source.refresh,
                            loadState.source.append,
                            loadState.source.prepend,
                            loadState.refresh,
                            loadState.append,
                            loadState.prepend
                        )
                        statesNeedToCheck.find {
                            it is LoadState.Error
                        }?.let {
                            Toast.makeText(
                                root.context,
                                "\uD83D\uDE28 Wooops ${(it as? LoadState.Error)?.error}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        log("trigger visible")
                    }
            }
        }
    }
}

fun log(message: String) {
    Log.e("khaitdt", message)
}