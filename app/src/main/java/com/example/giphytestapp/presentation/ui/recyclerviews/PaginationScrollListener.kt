package com.example.giphytestapp.presentation.ui.recyclerviews

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(
    private val layoutManager: LinearLayoutManager
): RecyclerView.OnScrollListener() {
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItem = visibleCount + firstVisiblePosition

        if (!isLoading) {
            if (lastVisibleItem >= totalItemCount && firstVisiblePosition != 0) {
                loadMoreItems()
            }
        }
    }

    protected open fun loadMoreItems() {
        isLoading = true
    }

    fun loadingFinished() {
        isLoading = false
    }
}