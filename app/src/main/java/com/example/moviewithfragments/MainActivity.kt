package com.example.moviewithfragments

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat.getActionView
import com.example.moviewithfragments.adapters.MainAdapter
import com.example.moviewithfragments.databinding.ActivityMainBinding
import com.example.moviewithfragments.viewmodels.MovieListViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = getActionView(menu.findItem(R.id.search)) as SearchView
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                searchView.onActionViewExpanded()
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.search -> {
                finish()
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun searchMovieApi(
        query: String,
        year: Int,
        pageNumber: Int,
        movieListViewModel: MovieListViewModel
    ) {
        movieListViewModel.searchMoviesApi(query, year, pageNumber)

    }
}