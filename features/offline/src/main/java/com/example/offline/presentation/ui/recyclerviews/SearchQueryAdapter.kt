package com.example.offline.presentation.ui.recyclerviews

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.offline.R
import com.example.offline.domain.model.SearchQuery

class SearchQueryAdapter(
    private val contract: Contract
): RecyclerView.Adapter<SearchQueryAdapter.SearchQueryViewHolder>() {
    private var items: List<SearchQuery> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchQueryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.search_query_item, parent, false)

        return SearchQueryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchQueryViewHolder, position: Int) {
        holder.bind(items[position], contract)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<SearchQuery>) {
        items = newItems
        notifyDataSetChanged()
    }

    interface Contract {
        fun navigateToCollectionScreen(searchQuery: String)
    }

    class SearchQueryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: SearchQuery, contract: Contract) {
            (itemView as? TextView)?.text = model.value
            itemView.setOnClickListener {
                contract.navigateToCollectionScreen(model.value)
            }
        }
    }
}