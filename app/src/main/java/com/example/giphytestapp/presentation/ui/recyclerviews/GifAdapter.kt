package com.example.giphytestapp.presentation.ui.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.giphytestapp.R
import com.example.giphytestapp.domain.model.GifModel

class GifAdapter(
    private val contract: Contract,
    private val type: Int
) : RecyclerView.Adapter<GifAdapter.GifViewHolder>() {
    private var items: List<GifModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when(type) {
            TABLE -> GifViewHolder(
                layoutInflater.inflate(R.layout.table_item, parent, false)
            )
            else -> GifViewHolder(
                layoutInflater.inflate(R.layout.column_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bindView(items[position], contract, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<GifModel>) {
        items = newItems
        notifyDataSetChanged()
    }

    interface Contract {
        fun loadGifToView(model: GifModel, imageView: ImageView)

        fun onGifClicked(position: Int)
    }

    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(model: GifModel, contract: Contract, position: Int) {
            val imageView = itemView.findViewById<ImageView>(R.id.image_view)
            contract.loadGifToView(model, imageView)
            imageView.setOnClickListener { contract.onGifClicked(position) }
        }
    }

    companion object {
        const val TABLE = 0
        const val COLUMN = 1
    }
}