package com.example.giphytestapp.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.giphytestapp.databinding.ActivityMainBinding
import com.example.giphytestapp.presentation.common.CollectionScreenState
import com.example.giphytestapp.presentation.common.CollectionScreenViewModel
import com.example.giphytestapp.presentation.ui.fragments.CollectionFragment
import com.example.giphytestapp.presentation.ui.fragments.ColumnFragment
import com.example.giphytestapp.presentation.ui.fragments.ProgressFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<CollectionScreenViewModel>()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        binding.viewpager.adapter = ViewPagerAdapter(supportFragmentManager)
//        binding.tabLayout.setupWithViewPager(binding.viewpager)
        setupStateObserver()
        setContentView(binding.root)

//        Log.d("AAADIP", "binding.frameLayout.id: ${binding.frameLayout.id}")
    }

    private fun setupStateObserver() {
        viewModel.state.observe(this) {
//            val nextFragment = if (it.isLoading) {
//                ProgressFragment()
//            } else if (it.error.isNotEmpty()) {
//                TextMessageFragment(it.error)
//            }
//            else {
//                CollectionFragment()
//            }

            Log.i("AAADIP", "in activity CollectionScreenState: $it")
            supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, CollectionFragment()).commit()
        }
    }

    inner class StandardGifOperationsContract {
        fun searchGifs(searchQuery: String) {
            viewModel.getGifs(searchQuery)
        }

//        fun loadNextPage() {
//            viewModel.getGifs()
//        }
    }
}