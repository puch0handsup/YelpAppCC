package com.example.yelpappcc.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.yelpappcc.R
import com.example.yelpappcc.databinding.FragmentBussinessDetailBinding
import com.example.yelpappcc.presentation.view.adapter.ReviewListAdapter
import com.example.yelpappcc.utils.BaseFragment
import com.example.yelpappcc.utils.UIState

class BusinessHistoryDetailFragment : BaseFragment() {

    private val binding by lazy {
        FragmentBussinessDetailBinding.inflate(layoutInflater)
    }

    private val reviewsAdapter = ReviewListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val businessItem = yelpViewModel.selectedBusinessHistory
        binding.rvReviewsList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = reviewsAdapter
        }
        binding.ratingBar.rating = businessItem?.rating!!.toFloat()
        binding.tvReviewCount.text = businessItem.reviewCount.toString()
        binding.tvRestaurantPrice.text = businessItem.price?: "New"
        binding.tvRestaurantName.text = businessItem.name

        Glide.with(binding.root)
            .load(businessItem.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.baseline_person_24)
            .error(R.drawable.baseline_person_24)
            .into(binding.ivDetailImg)
        getReviews()
        yelpViewModel.getReviews(businessItem.id as String)
        return binding.root
    }
//
    private fun getReviews() {
        yelpViewModel.reviews.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    reviewsAdapter.updateReviews(state.response!!)
                }
                is UIState.ERROR -> {}
            }
        }
    }

}