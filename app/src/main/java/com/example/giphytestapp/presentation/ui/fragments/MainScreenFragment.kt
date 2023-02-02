package com.example.giphytestapp.presentation.ui.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.giphytestapp.R
import com.example.giphytestapp.databinding.FragmentMainScreenBinding
import com.example.giphytestapp.presentation.ui.recyclerviews.ViewPagerAdapter
import com.example.giphytestapp.presentation.viewmodels.CollectionViewModel
import com.example.giphytestapp.presentation.viewmodels.NavigationViewModel
import com.example.offline.presentation.ui.fragments.SearchQueryFragment
import com.example.offline.presentation.viewmodels.NetworkViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainScreenFragment : Fragment(), CollectionFragment.ControlsContract {
    private val networkVm by sharedViewModel<NetworkViewModel>()
    private val navigationVm by sharedViewModel<NavigationViewModel>()
    private val collectionVm by sharedViewModel<CollectionViewModel>()

    private var searchViewVisible = false

    private val binding: FragmentMainScreenBinding by lazy {
        FragmentMainScreenBinding.inflate(layoutInflater)
    }

    override fun onScrollUp() {
        setActionButtonIcon(R.drawable.ic_search)
        binding.floatingButton.show()
    }

    override fun onScrollDown() {
        if (searchViewVisible) { closeSearchBar() }
        binding.floatingButton.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupTabLayout()
        setupSearchView()
        setupActionButton()
        return binding.root
    }

    private fun setupSearchView() {
        val onQueryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?) = true

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { queryText ->
                    closeSearchBar()
                    binding.searchView.setQuery("", false)

                    collectionVm.getGifs(searchQuery = queryText, queryIsNew = true,
                        navigationModel = navigationVm, online = networkVm.stateOnline.value ?: true)
                }
                return true
            }
        }
        binding.searchView.setOnQueryTextListener(onQueryTextListener)
    }

    private fun setupTabLayout() {
        ViewPagerAdapter(this).let {
            binding.viewpager.adapter = it
            TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
                tab.text = it.getTabTitle(position)
            }.attach()
        }
    }

    private fun setupActionButton() {
        binding.floatingButton.setOnClickListener {
            if (networkVm.stateOnline.value == false) {
                navigationVm.navigateTo(SearchQueryFragment(), null)
            } else {
                if (!searchViewVisible) { openSearchBar() } else { closeSearchBar() }
            }
        }
    }

    private fun setActionButtonIcon(resource: Int) {
        binding.floatingButton.setImageResource(resource)
    }

    private fun processConstraintSet(constraintSet: ConstraintSet) {
        val transition: Transition = ChangeBounds().apply {
            interpolator = AnticipateOvershootInterpolator(1.0f)
            duration = 100
        }

        TransitionManager.beginDelayedTransition(binding.constraintLayout, transition)
        constraintSet.applyTo(binding.constraintLayout)
    }

    private fun openSearchBar() {
        val constraintSet = ConstraintSet().also {
            it.clone(binding.constraintLayout)
        }

        constraintSet.connect(binding.tabLayout.id, ConstraintSet.TOP,
            binding.searchView.id, ConstraintSet.BOTTOM, 0)

        processConstraintSet(constraintSet)
        setActionButtonIcon(R.drawable.ic_cross)
        searchViewVisible = !searchViewVisible
    }

    private fun closeSearchBar() {
        val constraintSet = ConstraintSet().also {
            it.clone(binding.constraintLayout)
        }

        constraintSet.connect(binding.tabLayout.id, ConstraintSet.TOP,
            binding.constraintLayout.id, ConstraintSet.TOP, 0)

        processConstraintSet(constraintSet)
        setActionButtonIcon(R.drawable.ic_search)
        searchViewVisible = !searchViewVisible
        hideKeyboard()
    }

    private fun hideKeyboard() {
        (activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager).let {
            val view = requireActivity().currentFocus ?: View(activity)
            it?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}