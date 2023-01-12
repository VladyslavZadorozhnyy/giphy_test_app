package com.example.giphytestapp.presentation.ui.recyclerviews

class CollectionScrollListener(
    private val scrollUpCallback: () -> Unit,
    private val scrollDownCallback: () -> Unit,
    private val buttonClickedCallback: () -> Unit
) {
    fun onScrolledUp() {
        scrollUpCallback.invoke()
    }

    fun onScrolledDown() {
        scrollDownCallback.invoke()
    }

    fun onFloatingButtonClicked() {
        buttonClickedCallback.invoke()
    }
}