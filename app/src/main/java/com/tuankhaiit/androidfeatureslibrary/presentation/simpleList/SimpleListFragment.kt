package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.tuankhaiit.androidfeatureslibrary.R
import com.tuankhaiit.androidfeatureslibrary.databinding.FragmentSimpleListBinding
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleModel
import com.tuankhaiit.androidfeatureslibrary.presentation.base.BaseFragment
import com.tuankhaiit.androidfeatureslibrary.presentation.common.CommonLoadStateAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.adapter.SimpleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SimpleListFragment : BaseFragment() {
    private lateinit var binding: FragmentSimpleListBinding
    private val viewModel: SimpleListViewModel by viewModels()

    private lateinit var simpleAdapter: SimpleAdapter

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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.pagingDataFlow.collectLatest {
                    simpleAdapter.submitData(it)
                }
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                simpleAdapter.refresh()
                binding.refreshLayout.isRefreshing = false
            }
        }
    }

    private fun setupRecyclerView() {
        simpleAdapter = SimpleAdapter()

        binding.rvList.let { rvList ->
            rvList.layoutManager = LinearLayoutManager(requireContext())
            rvList.adapter = simpleAdapter.withLoadStateHeaderAndFooter(
                header = CommonLoadStateAdapter { simpleAdapter.retry() },
                footer = CommonLoadStateAdapter { simpleAdapter.retry() }
            )
        }
    }

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
}