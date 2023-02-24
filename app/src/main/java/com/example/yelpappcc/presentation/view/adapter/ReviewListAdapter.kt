package com.example.yelpappcc.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yelpappcc.R
import com.example.yelpappcc.databinding.ReviewItemBinding
import com.example.yelpappcc.domain.model.Review

class ReviewListAdapter(
    private var itemSet: MutableList<Review> = mutableListOf()
) :  RecyclerView.Adapter<ReviewsListViewHolder>(){

    fun updateReviews(newItems : List<Review>) {
        if (itemSet != newItems){
            itemSet.clear()
            itemSet.addAll(newItems)

            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsListViewHolder =
        ReviewsListViewHolder(
            ReviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = itemSet.size

    override fun onBindViewHolder(holder: ReviewsListViewHolder, position: Int) {
        holder.bind(itemSet[position])
    }

}

class ReviewsListViewHolder(
    private val binding : ReviewItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : Review) {
        binding.tvText.text = item.text
        binding.ratingBar.rating = item.rating!!.toFloat()
        binding.tvCustomerName.text = item.userName
        binding.tvReviewDate.text = item.timeCreated
        Glide
            .with(binding.root)
            .load(item.userImageUrl)
            .circleCrop()
            .placeholder(R.drawable.baseline_person_pin_24)
            .error(R.drawable.baseline_person_pin_24)
            .into(binding.ivCustomerImg)
    }
}