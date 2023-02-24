package com.example.yelpappcc.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yelpappcc.R
import com.example.yelpappcc.databinding.FragmentSearchHistoryBinding
import com.example.yelpappcc.domain.model.SearchLocationHistory
import com.example.yelpappcc.presentation.view.adapter.HistoryListAdapter
import com.example.yelpappcc.utils.BaseFragment
import com.example.yelpappcc.utils.UIState

class SearchHistoryFragment : BaseFragment() {

    private val binding by lazy {
        FragmentSearchHistoryBinding.inflate(layoutInflater)
    }

    private val historyListAdapter by lazy {
        HistoryListAdapter {
            yelpViewModel.businessHistoryIdList = it.businessIdList ?: listOf()
            findNavController().navigate(R.id.action_navigate_to_business_history_fragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvSearchList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = historyListAdapter
        }


        getHistory()
        yelpViewModel.getHistory()

        return binding.root
    }

    private fun getHistory() {
        yelpViewModel.history.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    historyListAdapter.updateBusinesses(state.response as List<SearchLocationHistory>)
                }
                is UIState.ERROR -> {}
            }
        }
    }

}