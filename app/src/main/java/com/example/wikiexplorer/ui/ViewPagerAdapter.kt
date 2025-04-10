package com.example.wikiexplorer.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wikiexplorer.ui.featured.FeaturedFragment
import com.example.wikiexplorer.ui.fragments.CategoryFragment
import com.example.wikiexplorer.ui.fragments.RandomFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RandomFragment()
            1 -> FeaturedFragment()
            2 -> CategoryFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
