package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuankhaiit.androidfeatureslibrary.databinding.FragmentSimpleListBinding
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleModel
import com.tuankhaiit.androidfeatureslibrary.presentation.base.BaseFragment
import com.tuankhaiit.androidfeatureslibrary.presentation.base.adapter.BaseAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter.CommonLoadStateAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.adapter.SimpleAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.githubRepo.RepoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SimpleListFragment : BaseFragment() {

    companion object {
        private const val DATA_KEY = "SimpleListFragment_DATA_KEY"

        fun createBundle(data: List<SimpleModel>): Bundle {
            return Bundle().apply {
                putParcelableArrayList(DATA_KEY, ArrayList(data))
            }
        }

        fun newInstance(arg: Bundle? = null): SimpleListFragment {
            return SimpleListFragment().apply {
                arguments = arg
            }
        }

        fun newInstance(data: List<SimpleModel>): SimpleListFragment {
            return newInstance(createBundle(data))
        }
    }

    private lateinit var binding: FragmentSimpleListBinding
    private val viewModel: SimpleListViewModel by viewModels()

    private lateinit var simpleAdapter: RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimpleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initEvents()


//        arguments?.getParcelableArrayList<SimpleModel>(DATA_KEY)?.let { array ->
//            submitList(array.toList().map { SimpleUI.from(it) })
//        }
    }

    private fun initViews() {
        setupRecyclerView()
    }

    private fun initEvents() {
        binding.bindState(simpleAdapter)
        binding.bindList(viewLifecycleOwner, viewModel, simpleAdapter)

        binding.refreshLayout.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                simpleAdapter.refresh()
                binding.refreshLayout.isRefreshing = false
            }
        }
    }

    private fun setupRecyclerView() {
        simpleAdapter = RepoAdapter()
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
    }

}

private fun FragmentSimpleListBinding.bindState(adapter: PagingDataAdapter<*, *>) {
    val refresh = CommonLoadStateAdapter { adapter.retry() }
    val header = CommonLoadStateAdapter { adapter.retry() }
    val footer = CommonLoadStateAdapter { adapter.retry() }
    adapter.addLoadStateListener { loadStates ->
        refresh.loadState = loadStates.refresh
        header.loadState = loadStates.prepend
        footer.loadState = loadStates.append
    }
    rvList.adapter = ConcatAdapter(refresh, header, adapter, footer)
}

private fun FragmentSimpleListBinding.bindList(
    lifecycleOwner: LifecycleOwner,
    viewModel: SimpleListViewModel,
    adapter: RepoAdapter,
) {
    lifecycleOwner.run {
        rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
        })

        val notLoading = adapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.source.refresh is LoadState.NotLoading }

        lifecycleScope.launch {
            notLoading.collectLatest {
                log("trigger notLoading")
            }
        }

//        val hasNotScrolledForCurrentSearch = uiState
//            .map { it.hasNotScrolledForCurrentSearch }
//            .distinctUntilChanged()

//        val shouldScrollToTop = combine(
//            notLoading,
//            hasNotScrolledForCurrentSearch,
//            Boolean::and
//        )
//            .distinctUntilChanged()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.pagingDataFlow.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

//        lifecycleScope.launch {
//            shouldScrollToTop.collect { shouldScroll ->
//                if (shouldScroll) list.scrollToPosition(0)
//            }
//        }

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

fun log(message: String) {
    Log.e("khaitdt", message)
}