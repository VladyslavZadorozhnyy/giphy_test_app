package com.example.giphytestapp.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cache.data.common.GifEntity
import com.example.cache.data.database.GifCache
import com.example.giphytestapp.R
import com.example.giphytestapp.data.remote.GiphyAPI
import com.example.giphytestapp.data.repository.GifRepositoryImpl
import com.example.giphytestapp.domain.repository.GifRepository
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProgressFragment : Fragment() {
    private val gifRepository: GifRepository by inject()
    private val gifCache: GifCache by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        GlobalScope.launch {
//            val a = Retrofit.Builder()
//                .baseUrl("https://api.giphy.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(GiphyAPI::class.java)

//            Log.d("AAADIP", "\n\n\n\n 3 - (injected repo) result: ${repo.getGifs("mercedes")}")

//            Log.d("AAADIP", "starting")
//            try {
//                val db= GifRoomDatabase.getDatabase(requireActivity().application)
//                Log.d("AAADIP", "Done successfully 2")
//            } catch (e: Exception) {
//                Log.d("AAADIP", "exception: $e")
//            }
//        }

//        GlobalScope.launch {
//            withContext(Dispatchers.Main) {
//                delay(3000)
//                Log.d("AAADIP", "Showing dialog")
//                val customDialog = CustomDialog(requireActivity())
//                customDialog.setCancelable(false)
//                customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                customDialog.show()
//            }
//        }

        GlobalScope.launch {
            try {
                gifCache.addToCache(
                    GifEntity(
                        width = 10,
                        height = 10,
                        hash = "101010101010",
                        filePath = "fjvnfjnvjfnvjfnvjnfv",
                        searchQuery = "some_search_query_1"
                    ),
                    null
                )

                Log.i("AAADIP", "success with addToCache")
            } catch (e: Exception) {
                Log.i("AAADIP", "fucked up with addToCache: $e")
            }

            try {
                val result = gifCache.getAllGifs()
                Log.i("AAADIP", "success with getAll: $result")
            } catch (e: Exception) {
                Log.i("AAADIP", "fucked up with getAll: $e")
            }
        }
    }
}