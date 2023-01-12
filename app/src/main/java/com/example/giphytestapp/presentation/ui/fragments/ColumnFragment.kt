package com.example.giphytestapp.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giphytestapp.R
import com.example.giphytestapp.databinding.FragmentRecyclerBinding
import com.example.giphytestapp.domain.model.GifModel
import com.example.giphytestapp.presentation.common.CollectionScreenViewModel
import com.example.giphytestapp.presentation.ui.activities.MainActivity
import com.example.giphytestapp.presentation.ui.recyclerviews.PaginationAdapter
import com.example.giphytestapp.presentation.ui.recyclerviews.PaginationScrollListener
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ColumnFragment: Fragment() {
    private val scrollSensitivityStep: Int = 25
    private val viewModel by viewModel<CollectionScreenViewModel>()

    private val scrollListener by lazy {
        (parentFragment as? CollectionFragment)?.scrollListener
    }

    private val binding by lazy {
        FragmentRecyclerBinding.inflate(layoutInflater)
    }

//    val permission = ActivityCompat.checkSelfPermission(
//        requireActivity(),
//        Manifest.permission.WRITE_EXTERNAL_STORAGE
//    )
//
//    val REQUEST_EXTERNAL_STORAGE = 1
//    val PERMISSIONS_STORAGE = arrayOf(
//        Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE
//    )
//
//    if (permission != PackageManager.PERMISSION_GRANTED) {
//        ActivityCompat.requestPermissions(
//            requireActivity(),
//            PERMISSIONS_STORAGE,
//            REQUEST_EXTERNAL_STORAGE
//        )
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        TODO: start
//        GlobalScope.launch(Dispatchers.IO) {
//            withContext(Dispatchers.Main) {
//                Glide.with(requireContext())
//                    .load("https://media3.giphy.com/media/ljGfAhpsZ17ws/giphy.gif?cid=9a2a502enjhhc0m4oh47v8bef5o072iou50qj1k9n619cxnm&rid=giphy.gif&ct=g")
//                    .into(binding.imageView)
//            }
////            val downloaded = Glide.with(requireContext())
////                .asGif()
////                .load("https://media3.giphy.com/media/ljGfAhpsZ17ws/giphy.gif?cid=9a2a502enjhhc0m4oh47v8bef5o072iou50qj1k9n619cxnm&rid=giphy.gif&ct=g")
////                .into(binding.imageView)
////                .submit()
////                .get()
//
////            saveImage(downloaded)
//
//            delay(2000)
//
////            try {
////                val byteBuffer = (binding.imageView.drawable as GifDrawable?)?.buffer
////                Log.d("AAADIPP", "byteBuffer: $byteBuffer")
////            } catch (e: Throwable) {
////                Log.d("AAADIPP", "e: $e")
////            }
//
//            val byteBuffer = (binding.imageView.drawable as GifDrawable?)?.buffer
//            val gifFile = File("${Environment.getExternalStorageDirectory().absolutePath}/Download/giphy", "sample.gif")
//
//            val output = FileOutputStream(gifFile)
//            val bytes = ByteArray(byteBuffer!!.capacity())
//            (byteBuffer.duplicate().clear() as ByteBuffer).get(bytes)
//            output.write(bytes, 0 ,bytes.size)
//            output.close()
//
//
//            withContext(Dispatchers.Main) {
//                Glide.with(requireContext())
//                    .load("https://media2.giphy.com/media/RlI76o55P9UyGNo1W1/giphy.gif?cid=9a2a502esc39q5wkizmauci78lomaaubdhjd224w0mi3giss&rid=giphy.gif&ct=g")
//                    .into(binding.imageView)
//            }
//
//
//            delay(5000)
//
//            withContext(Dispatchers.Main) {
//                Glide.with(requireContext())
//                    .asGif()
//                    .load(File("${Environment.getExternalStorageDirectory().absolutePath}/Download/giphy/sample.gif"))
//                    .into(binding.imageView)
//            }
//        }
//        TODO: end

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
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        setupAdapter(linearLayoutManager)
        setupScrollListener(linearLayoutManager)

//        loadFirstPage()
    }

    private fun setupAdapter(linearLayoutManager: LinearLayoutManager) {
        val paginationAdapter = PaginationAdapter<GifModel>(requireContext(), R.layout.column_item)

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
//                    (binding.recyclerView.adapter as? PaginationAdapter<GifModel>)?.addItem(null)
//                    delay(4000)
//                    (binding.recyclerView.adapter as? PaginationAdapter<GifModel>)?.removeLastItem()
////                    (binding.recyclerView.adapter as? PaginationAdapter)?.addItems(newPage)
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
//        (binding.recyclerView.adapter as? PaginationAdapter)?.addItems(newPage)
//    }
}