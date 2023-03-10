package com.example.giphytestapp.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giphytestapp.presentation.viewmodels.SearchesViewModel
import com.example.offline.databinding.FragmentSearchQueryBinding
import com.example.offline.presentation.ui.recyclerviews.SearchQueryAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchQueryFragment : Fragment() {
    private val binding by lazy { FragmentSearchQueryBinding.inflate(layoutInflater) }
    private val viewModel by sharedViewModel<SearchesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupRecyclerView()
        setupSearchQueriesObserver()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter =  SearchQueryAdapter().also {
            it.onClickListener = { value -> viewModel.navigateToCollection(value) }
        }
    }

    private fun setupSearchQueriesObserver() {
        viewModel.queriesState.observe(viewLifecycleOwner) { state ->
            (binding.recyclerView.adapter as? SearchQueryAdapter)?.updateItems(state.searchQueries)
        }
    }
}