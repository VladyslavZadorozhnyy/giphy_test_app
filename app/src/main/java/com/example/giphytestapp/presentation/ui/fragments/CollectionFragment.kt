package com.example.giphytestapp.presentation.ui.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.giphytestapp.R
import com.example.giphytestapp.databinding.FragmentCollectionBinding
import com.example.giphytestapp.presentation.ui.recyclerviews.CollectionScrollListener
import com.example.giphytestapp.presentation.ui.recyclerviews.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

open class CollectionFragment : Fragment() {
    private var searchViewVisible = false
    val scrollListener = CollectionScrollListener(
        scrollUpCallback = { onCollectionScrollUp() },
        scrollDownCallback = { onCollectionScrollDown() },
        buttonClickedCallback = { onFloatingButtonClicked() },
    )

    private val binding: FragmentCollectionBinding by lazy {
        FragmentCollectionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupTabLayout()
        setupActionButton()
        return binding.root
    }

    private fun setupTabLayout() {
        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewpager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = viewPagerAdapter.getTabTitle(position)
        }.attach()
    }

    private fun setupActionButton() {
        binding.floatingButton.setOnClickListener {
            scrollListener.onFloatingButtonClicked()
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

        constraintSet.connect(
            binding.tabLayout.id,
            ConstraintSet.TOP,
            binding.searchView.id,
            ConstraintSet.BOTTOM,
            0
        )

        processConstraintSet(constraintSet)
        setActionButtonIcon(R.drawable.ic_cross)
    }

    private fun closeSearchBar() {
        val constraintSet = ConstraintSet().also {
            it.clone(binding.constraintLayout)
        }

        constraintSet.connect(
            binding.tabLayout.id,
            ConstraintSet.TOP,
            binding.constraintLayout.id,
            ConstraintSet.TOP,
            0
        )

        processConstraintSet(constraintSet)
        setActionButtonIcon(R.drawable.ic_search)
        hideKeyboard()
    }

    private fun hideKeyboard() {
        (activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager).let {
            val view = requireActivity().currentFocus ?: View(activity)
            it?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun onCollectionScrollUp() {
        setActionButtonIcon(R.drawable.ic_search)
        binding.floatingButton.show()
    }

    private fun onCollectionScrollDown() {
        if (searchViewVisible) {
            closeSearchBar()
            searchViewVisible = false
        }

        binding.floatingButton.hide()
    }

    private fun onFloatingButtonClicked() {
        if (!searchViewVisible) {
            openSearchBar()
        } else {
            closeSearchBar()
        }
        searchViewVisible = !searchViewVisible
    }
}