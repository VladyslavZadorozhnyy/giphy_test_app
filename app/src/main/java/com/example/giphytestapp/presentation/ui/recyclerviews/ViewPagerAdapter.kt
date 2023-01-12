package com.example.giphytestapp.presentation.ui.recyclerviews

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.giphytestapp.presentation.ui.fragments.CollectionFragment
import com.example.giphytestapp.presentation.ui.fragments.ColumnFragment
import com.example.giphytestapp.presentation.ui.fragments.TableFragment

class ViewPagerAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {
    private val tabsTitles = listOf("Column view", "Table view")

    override fun getItemCount(): Int {
        return tabsTitles.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ColumnFragment()
            else -> TableFragment()
        }
    }

    fun getTabTitle(position: Int): String {
        return tabsTitles[position]
    }
}