package com.example.offline.presentation.ui.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.offline.R
import com.example.offline.domain.model.SearchQuery

class SearchQueryAdapter: RecyclerView.Adapter<SearchQueryAdapter.SearchQueryViewHolder>() {
    private var items: List<SearchQuery> = listOf()
    var onClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchQueryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.search_query_item, parent, false)

        return SearchQueryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchQueryViewHolder, position: Int) {
        holder.bind(items[position], onClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<SearchQuery>) {
        items = newItems
        notifyDataSetChanged()
    }

    class SearchQueryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: SearchQuery, onClickListener: ((String) -> Unit)?) {
            (itemView as? TextView)?.text = model.value
            itemView.setOnClickListener { onClickListener?.invoke(model.value) }
        }
    }
}