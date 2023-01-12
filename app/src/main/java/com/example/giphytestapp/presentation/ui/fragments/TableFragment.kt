package com.example.giphytestapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giphytestapp.R
import com.example.giphytestapp.databinding.FragmentRecyclerBinding
import com.example.giphytestapp.domain.model.GifModel
import com.example.giphytestapp.presentation.common.CollectionScreenViewModel
import com.example.giphytestapp.presentation.ui.recyclerviews.PaginationAdapter
import com.example.giphytestapp.presentation.ui.recyclerviews.PaginationScrollListener
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableFragment : Fragment() {
    private val columnsNumber = 3
    private val scrollSensitivityStep: Int = 25
    private val viewModel by viewModel<CollectionScreenViewModel>()

    private val scrollListener by lazy {
        (parentFragment as? CollectionFragment)?.scrollListener
    }

    private val binding by lazy {
        FragmentRecyclerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

//        val linearLayoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
//        val paginationAdapter = PaginationAdapter(requireContext())
//
//        binding.recyclerView.layoutManager = linearLayoutManager
//        binding.recyclerView.adapter = paginationAdapter

//        binding.recyclerView.addOnScrollListener(
//            object : PaginationScrollListener(linearLayoutManager) {
//                override fun loadMoreItems() {
//                    currentPage += 1
//                    loadNextPage()
//                }
//
//                override fun isLastPage(): Boolean {
////                    return isLastPage()
//                    return true
//                }
//
//                override fun isLoading(): Boolean {
////                    return isLoading()
//                    return true
//                }
//            }
//        )

//        loadFirstPage()
//        loadNextPage()
        setupStateObserver()
        setupRecyclerView()
        return binding.root
    }

    private fun setupStateObserver() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (!state.isLoading && state.error.isEmpty()) {
                (binding.recyclerView.adapter as? PaginationAdapter<GifModel>)?.addItems(state.gifs)
            }
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = GridLayoutManager(
            requireContext(),
            columnsNumber,
            LinearLayoutManager.VERTICAL,
            false
        )

        setupAdapter(linearLayoutManager)
        setupScrollListener(linearLayoutManager)

//        loadFirstPage()
    }

    private fun setupAdapter(linearLayoutManager: LinearLayoutManager) {
        val paginationAdapter = PaginationAdapter<GifModel>(requireContext(), R.layout.table_item)

        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = paginationAdapter
    }

    private fun setupScrollListener(linearLayoutManager: LinearLayoutManager) {
        binding.recyclerView.addOnScrollListener(
            object : PaginationScrollListener(linearLayoutManager) {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy < -scrollSensitivityStep) {
                        scrollListener?.onScrolledUp()
                    } else if (dy > scrollSensitivityStep) {
                        scrollListener?.onScrolledDown()
                    }
                }

                override fun loadMoreItems() {
                    currentPage += 1
                    if (currentPage == 1) {
//                        loadNextPage()
                    }
                }

                override fun isLastPage(): Boolean {
                    return false
                }

                override fun isLoading(): Boolean {
                    return false
                }
            }
        )
    }

//    private fun loadNextPage() {
//        val newPage = listOf(
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media1.giphy.com/media/yNs2a0jRkYxy6191B2/giphy.gif?cid=ecf05e47kabqop5mr0fp70trvb5kyheh1fwdehrxz5m84hmw&rid=giphy.gif&ct=g",
//        )
//
//        GlobalScope.launch {
//            runBlocking {
//                withContext(Dispatchers.Main) {
//                    delay(2000)
//                    (binding.recyclerView.adapter as? PaginationAdapter)?.removeLastItem()
////                    delay(2000)
//                    (binding.recyclerView.adapter as? PaginationAdapter)?.addItems(newPage)
//                }
//            }
//        }
//    }

//    private fun loadFirstPage() {
//        val newPage = listOf(
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g",
//            "https://media1.giphy.com/media/yNs2a0jRkYxy6191B2/giphy.gif?cid=ecf05e47kabqop5mr0fp70trvb5kyheh1fwdehrxz5m84hmw&rid=giphy.gif&ct=g",
//            "https://media1.giphy.com/media/yNs2a0jRkYxy6191B2/giphy.gif?cid=ecf05e47kabqop5mr0fp70trvb5kyheh1fwdehrxz5m84hmw&rid=giphy.gif&ct=g",
//        )
//
//        (binding.recyclerView.adapter as? PaginationAdapter<GifModel>)?.addItems(newPage)
//    }
}