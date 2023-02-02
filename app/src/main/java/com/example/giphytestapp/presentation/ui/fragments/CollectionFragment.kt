package com.example.giphytestapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.giphytestapp.common.Constants
import com.example.giphytestapp.databinding.FragmentCollectionBinding
import com.example.giphytestapp.domain.model.GifModel
import com.example.giphytestapp.presentation.ui.fragments.DetailedFragment.Companion.DETAILED_GIF_KEY
import com.example.giphytestapp.presentation.ui.recyclerviews.GifAdapter
import com.example.giphytestapp.presentation.ui.recyclerviews.PaginationScrollListener
import com.example.giphytestapp.presentation.viewmodels.CollectionViewModel
import com.example.giphytestapp.presentation.viewmodels.NavigationViewModel
import com.example.offline.presentation.viewmodels.NetworkViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CollectionFragment: Fragment(), GifAdapter.Contract {
    private val columnsNumber = 3
    private val scrollSensitivityStep: Int = 25
    private var scrollListener: PaginationScrollListener? = null

    private val networkVm by sharedViewModel<NetworkViewModel>()
    private val navigationVm by sharedViewModel<NavigationViewModel>()
    private val collectionVm by sharedViewModel<CollectionViewModel>()

    private val recyclerViewAdapter by lazy { binding.recyclerView.adapter as? GifAdapter }

    private val binding by lazy {
        FragmentCollectionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupStateObserver()

        if (arguments?.getString(TYPE_KEY) == COLUMN_TYPE) {
            setupColumnRecycler()
        } else {
            setupTableRecycler()
        }

        return binding.root
    }

    override fun loadGifToView(model: GifModel, imageView: ImageView) {
        val circularProgressDrawable = CircularProgressDrawable(requireContext()).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
        collectionVm.loadGifToView(model, imageView, circularProgressDrawable)
    }

    override fun onGifClicked(position: Int) {
        navigationVm.navigateTo(
            DetailedFragment(),
            Bundle().also { it.putInt(DETAILED_GIF_KEY, position) }
        )
    }

    private fun setupStateObserver() {
        collectionVm.state.observe(viewLifecycleOwner) { state ->
            if (state.gifs.isNotEmpty()) {
                recyclerViewAdapter?.updateItems(state.gifs)
            }

            if (state.gifs.isNotEmpty() && state.gifs.last() != Constants.LOADING_ITEM) {
                scrollListener?.loadingFinished()
            }
        }
    }

    private fun setupColumnRecycler() {
        val columnLinearManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.adapter = GifAdapter(this, GifAdapter.COLUMN)
        binding.recyclerView.layoutManager = columnLinearManager
        setupScrollListener(columnLinearManager)
    }

    private fun setupTableRecycler() {
        val tableLinearManager = GridLayoutManager(requireContext(), columnsNumber,
            LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.adapter = GifAdapter(this, GifAdapter.TABLE)
        binding.recyclerView.layoutManager = tableLinearManager
        setupScrollListener(tableLinearManager)
    }

    private fun setupScrollListener(linearLayoutManager: LinearLayoutManager) {
        scrollListener = object : PaginationScrollListener(linearLayoutManager) {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < -scrollSensitivityStep) {
                    (parentFragment as? ControlsContract)?.onScrollUp()
                } else if (dy > scrollSensitivityStep) {
                    (parentFragment as? ControlsContract)?.onScrollDown()
                }
            }

            override fun loadMoreItems() {
                super.loadMoreItems()

                collectionVm.getGifs(queryIsNew = false, navigationModel = navigationVm,
                    online = networkVm.stateOnline.value ?: true)
            }
        }
        scrollListener?.let {
            binding.recyclerView.addOnScrollListener(it)
        }
    }

    interface ControlsContract {
        fun onScrollUp ()

        fun onScrollDown ()
    }

    companion object {
        const val TYPE_KEY = "key"
        const val TABLE_TYPE = "Table"
        const val COLUMN_TYPE = "Column"
    }
}