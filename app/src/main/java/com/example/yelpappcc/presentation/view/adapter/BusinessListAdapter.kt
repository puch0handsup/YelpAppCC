package com.example.yelpappcc.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yelpappcc.R
import com.example.yelpappcc.databinding.BusinessItemBinding
import com.example.yelpappcc.domain.model.Business

class BusinessListAdapter(
    private val itemSet : MutableList<Business> = mutableListOf(),
    private val onClickBusiness : (item: Business) -> Unit
) : RecyclerView.Adapter<BusinessListViewHolder>() {

    fun updateBusinesses(newItems : List<Business>) {
        if (itemSet != newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)

            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessListViewHolder =
        BusinessListViewHolder(
            BusinessItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = itemSet.size

    override fun onBindViewHolder(holder: BusinessListViewHolder, position: Int) {
        holder.bind(itemSet[position], onClickBusiness)
    }
}

class BusinessListViewHolder(
    private val binding: BusinessItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
    private fun Double.toMiles(): String = (this * 0.000189394).format(2)
    fun bind(item : Business, onClickBusiness: (Business) -> Unit) {
        binding.tvBusinessName.text = item.name
        binding.ratingBar.rating = item.rating!!.toFloat()
        binding.tvReviewCount.text = item.reviewCount.toString()
        binding.tvRestaurantPrice.text = item.price?: "New"
        binding.tvDistance.text = "${item.distance?.toMiles()} mi away"
        binding.tvCity.text = item.location?.city

        itemView.setOnClickListener {
            item.let { onClickBusiness(it) }
        }

        Glide
            .with(binding.root)
            .load(item.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.baseline_fastfood_24)
            .error(R.drawable.baseline_no_food_24)
            .into(binding.ivRestaurantThumb)
    }
}