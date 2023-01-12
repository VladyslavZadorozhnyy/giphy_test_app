package com.example.giphytestapp.presentation.ui.recyclerviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.giphytestapp.R
import com.example.cache.data.common.Constants
import com.example.giphytestapp.domain.model.GifModel
import java.io.File

class PaginationAdapter<T>(
    private val context: Context,
    private val itemViewRes: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemsList: List<T?> = listOf()

    private val inflater = LayoutInflater.from(context)
    private val loadingViewRes = R.layout.loading_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ItemViewType.LOADING.value) {
            val viewItem = inflater.inflate(loadingViewRes, parent, false)
            return LoadingViewHolder(viewItem)
        }

        val viewItem = inflater.inflate(itemViewRes, parent, false)
        return if (itemViewRes == R.layout.column_item || itemViewRes == R.layout.table_item) {
            GifViewHolder(viewItem)
        } else {
            SearchViewHolder(viewItem)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? LoadingViewHolder)?.let {
            return
        }
        (holder as? SearchViewHolder)?.let { viewHolder ->
            itemsList[position]?.let {
                val view: TextView? = viewHolder.itemView as? TextView
                viewHolder.bindView(view, it as String)
            }
        }
        (holder as? GifViewHolder)?.let { viewHolder ->
            itemsList[position]?.let {
                val view: ImageView? = viewHolder.itemView as? ImageView
                viewHolder.bindView(view, it as GifModel, context)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemsList[position] == null) {
            ItemViewType.LOADING.value
        } else {
            ItemViewType.REGULAR.value
        }
    }

    fun addItem(item: T?) {
        itemsList = itemsList + listOf(item)
        notifyItemInserted(itemsList.size - 1)
    }

    fun removeLastItem() {
        val itemsListCopy = itemsList.subList(0, itemsList.size - 1)
        notifyItemRemoved(itemsList.size - 1)

        itemsList = itemsListCopy
    }

    fun addItems(items: List<T?>) {
        items.forEach {
            addItem(it)
        }
    }

    enum class ItemViewType(val value: Int) {
        LOADING(0),
        REGULAR(1)
    }

    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(view: ImageView?, model: GifModel, context: Context) {
            view?.let {
                val circularProgressDrawable = CircularProgressDrawable(context).apply {
                    strokeWidth = 5f
                    centerRadius = 30f
                    start()
                }

                if (model.url.isNotEmpty()) {
                    Glide.with(context)
                        .load(model.url)
                        .placeholder(circularProgressDrawable)
                        .into(it)
                } else {
                    Glide.with(context)
                        .load(File(Constants.STORAGE_DIR_PATH, model.filePath))
                        .placeholder(circularProgressDrawable)
                        .into(it)
                }
            }
        }
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(view: TextView?, searchQuery: String?) {
            view?.text = searchQuery
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}