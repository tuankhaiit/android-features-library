package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuankhaiit.androidfeatureslibrary.databinding.FragmentSimpleListBinding
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleModel
import com.tuankhaiit.androidfeatureslibrary.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

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

    lateinit var binding: FragmentSimpleListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimpleListBinding.inflate(inflater, container, false)
        return binding.root
    }
}
