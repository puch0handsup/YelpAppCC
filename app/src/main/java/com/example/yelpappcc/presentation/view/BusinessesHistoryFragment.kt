package com.example.yelpappcc.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yelpappcc.R
import com.example.yelpappcc.databinding.FragmentBusinessesListBinding
import com.example.yelpappcc.presentation.view.adapter.BusinessListAdapter
import com.example.yelpappcc.utils.BaseFragment
import com.example.yelpappcc.utils.UIState

class BusinessesHistoryFragment : BaseFragment() {

    private val binding by lazy {
        FragmentBusinessesListBinding.inflate(layoutInflater)
    }

    private val businessListAdapter by lazy {
        BusinessListAdapter {
            yelpViewModel.selectedBusinessHistory = it
            findNavController().navigate(R.id.action_navigate_to_history_detail_fragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvBusinessList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = businessListAdapter
        }

        getBusinessesList()
        yelpViewModel.getHistory()

        return binding.root
    }

    private fun getBusinessesList() {
        yelpViewModel.businesses.observe(viewLifecycleOwner){ state ->
            when(state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    businessListAdapter.updateBusinesses(state.response!!)
                }
                is UIState.ERROR -> {}
            }
        }
    }
}