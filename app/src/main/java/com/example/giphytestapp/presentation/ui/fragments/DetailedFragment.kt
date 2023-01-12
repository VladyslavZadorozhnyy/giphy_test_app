package com.example.giphytestapp.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.giphytestapp.databinding.FragmentDetailedBinding
import com.example.giphytestapp.presentation.ui.recyclerviews.DetailedAdapter


class DetailedFragment : Fragment() {
    private val binding by lazy { FragmentDetailedBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val strings = listOf("one", "two", "three")
        val detailedAdapter = DetailedAdapter(strings)

        binding.viewPager2.adapter = detailedAdapter
        binding.viewPager2.setCurrentItem(1, true)
    }
}