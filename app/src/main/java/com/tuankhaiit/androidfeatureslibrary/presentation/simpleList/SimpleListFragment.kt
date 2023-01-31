package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuankhaiit.androidfeatureslibrary.R
import com.tuankhaiit.androidfeatureslibrary.presentation.base.BaseFragment
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.adapter.SimpleAdapter
import com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.model.SimpleItem
import com.tuankhaiit.androidfeatureslibrary.presentation.extension.paddingToFitBottomNavigation
import com.tuankhaiit.androidfeatureslibrary.presentation.extension.marginToFitStatusBar

class SimpleListFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simple_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        view?.findViewById<Toolbar>(R.id.toolbar)?.apply {
            marginToFitStatusBar()
            requireBaseActivity()?.setSupportActionBar(this)
        }
    }

    private fun setupRecyclerView() {
        view?.findViewById<RecyclerView>(R.id.rvList)?.let { rvList ->
            rvList.layoutManager = LinearLayoutManager(requireContext())
            rvList.adapter = SimpleAdapter().apply {
                arguments?.getParcelableArrayList<SimpleItem>(DATA_KEY)?.let {
                    submitList(it.toList())
                }
            }
            rvList.paddingToFitBottomNavigation()
        }
    }

    companion object {
        private const val DATA_KEY = "SimpleListFragment_DATA_KEY"

        fun createBundle(data: List<SimpleItem>): Bundle {
            return Bundle().apply {
                putParcelableArrayList(DATA_KEY, ArrayList(data))
            }
        }

        fun newInstance(arg: Bundle? = null): SimpleListFragment {
            return SimpleListFragment().apply {
                arguments = arg
            }
        }

        fun newInstance(data: List<SimpleItem>): SimpleListFragment {
            return newInstance(createBundle(data))
        }
    }
}