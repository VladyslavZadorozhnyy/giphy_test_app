package com.example.giphytestapp.presentation.ui.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.giphytestapp.common.Constants.LOADING_ITEM
import com.example.giphytestapp.databinding.DetailedItemBinding
import com.example.giphytestapp.domain.model.GifModel

class GifDetailedAdapter(
    private val contract: Contract
): RecyclerView.Adapter<GifDetailedAdapter.DetailedViewHolder>() {
    private var items: List<GifModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DetailedItemBinding.inflate(layoutInflater, parent, false)
        return DetailedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailedViewHolder, position: Int) {
        holder.bind(items[position], contract, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<GifModel>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun firstSetup(): Boolean {
        return items.isEmpty()
    }

    interface Contract {
        fun onRemoveButtonClicked(position: Int)

        fun loadGifToView(model: GifModel, imageView: ImageView)
    }

    class DetailedViewHolder(
        private val binding: DetailedItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: GifModel, contract: Contract, position: Int) {
            if (model == LOADING_ITEM) {
                binding.removeButton.visibility = View.INVISIBLE
            } else {
                binding.removeButton.visibility = View.VISIBLE
            }

            contract.loadGifToView(model, binding.imageView)
            binding.removeButton.setOnClickListener {
                contract.onRemoveButtonClicked(position)
            }
        }
    }
}