package com.example.moviewithfragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviewithfragments.fragments.LatestMoviesFragment
import com.example.moviewithfragments.fragments.PopularMoviesFragment


class MainAdapter(fragmentActivity: FragmentActivity, private val tabTitles: Array<String>) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> LatestMoviesFragment()
            else -> PopularMoviesFragment()


        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    fun getTabTitle(position: Int): String {
        return tabTitles[position]
    }
}