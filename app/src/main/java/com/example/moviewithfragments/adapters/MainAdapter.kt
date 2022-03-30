package com.example.moviewithfragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviewithfragments.R
import com.example.moviewithfragments.fragments.LatestMoviesFragment
import com.example.moviewithfragments.fragments.PopularMoviesFragment


class MainAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val tabTitles = arrayOf(
        fragmentActivity.baseContext.resources.getString(R.string.popularMovies),
        fragmentActivity.baseContext.resources.getString(R.string.currentYearMovies)

        )
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularMoviesFragment()
            else -> LatestMoviesFragment()

        }
    }

    override fun getItemCount(): Int {
        return 2
    }
    fun getTabTitle(position : Int): String{
        return tabTitles[position]
    }
}