package com.example.moviewithfragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviewithfragments.fragments.LatestMoviesFragment
import com.example.moviewithfragments.fragments.PopularMoviesFragment

class MainAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularMoviesFragment()
            else -> LatestMoviesFragment()

        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}