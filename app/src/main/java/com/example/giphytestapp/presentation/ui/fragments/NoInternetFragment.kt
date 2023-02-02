package com.example.giphytestapp.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.giphytestapp.presentation.viewmodels.NavigationViewModel
import com.example.offline.databinding.NoInternetFragmentBinding
import com.example.giphytestapp.presentation.viewmodels.SearchesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class NoInternetFragment : Fragment() {
    private val actionBarShown: Boolean by lazy {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.isShowing ?: false
    }

    private val searchesVm by sharedViewModel<SearchesViewModel>()
    private val navigationVm by sharedViewModel<NavigationViewModel>()
    private val binding by lazy { NoInternetFragmentBinding.inflate(layoutInflater) }

    override fun onStart() {
        super.onStart()
        if (actionBarShown) {
            (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupButtons()
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        if (actionBarShown) {
            (requireActivity() as? AppCompatActivity)?.supportActionBar?.show()
        }
    }

    private fun setupButtons() {
        binding.exitApp.setOnClickListener { navigationVm.exitApplication() }

        binding.offlineButton.setOnClickListener { searchesVm.uploadSearchQueries() }
    }
}