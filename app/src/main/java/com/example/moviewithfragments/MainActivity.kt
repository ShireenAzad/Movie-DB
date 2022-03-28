package com.example.moviewithfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviewithfragments.adapters.MainAdapter
import com.example.moviewithfragments.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity :AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val tabLayout = activityMainBinding.tabs
        val viewPager2 = activityMainBinding.viewPager
        val adapter = MainAdapter(this)
        viewPager2.adapter = adapter
        val tabTitles = arrayOf("Popular Movies", "Latest Movies")
        TabLayoutMediator(
            tabLayout, viewPager2
        ) { tab, position -> tab.text = tabTitles[position] }.attach()

    }
}