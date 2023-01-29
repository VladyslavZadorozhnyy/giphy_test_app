package com.example.giphytestapp.presentation.ui.recyclerviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.giphytestapp.presentation.ui.fragments.CollectionFragment

class ViewPagerAdapter(
    parentFragment: Fragment
) : FragmentStateAdapter(parentFragment) {
    private val tabsTitles = listOf(
        "${CollectionFragment.COLUMN_TYPE} view",
        "${CollectionFragment.TABLE_TYPE} view"
    )

    override fun getItemCount(): Int {
        return tabsTitles.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = CollectionFragment()
        val bundle = Bundle()

        when (position) {
            0 -> bundle.putString(CollectionFragment.TYPE_KEY, CollectionFragment.COLUMN_TYPE)
            else -> bundle.putString(CollectionFragment.TYPE_KEY, CollectionFragment.TABLE_TYPE)
        }

        fragment.arguments = bundle
        return fragment
    }

    fun getTabTitle(position: Int): String {
        return tabsTitles[position]
    }
}