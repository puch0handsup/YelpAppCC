package com.example.yelpappcc.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yelpappcc.databinding.HistoryItemBinding
import com.example.yelpappcc.domain.model.SearchLocationHistory

class HistoryListAdapter(
    private var itemSet : MutableList<SearchLocationHistory> = mutableListOf(),
    private val onClickItem : (SearchLocationHistory) -> Unit
) : RecyclerView.Adapter<HistoryListViewHolder>() {

    fun updateBusinesses(newItems : List<SearchLocationHistory>){
        if (itemSet != newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListViewHolder =
        HistoryListViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = itemSet.size

    override fun onBindViewHolder(holder: HistoryListViewHolder, position: Int) {
        holder.bind(itemSet[position], onClickItem)
    }

}

class HistoryListViewHolder(
    private val binding : HistoryItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : SearchLocationHistory, onClickItem: (SearchLocationHistory) -> Unit) {
        binding.tvSearchName.text = item.searchLocationName

        itemView.setOnClickListener {
            onClickItem(item)
        }
    }


}
