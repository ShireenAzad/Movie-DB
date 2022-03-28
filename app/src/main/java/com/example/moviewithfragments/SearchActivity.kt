package com.example.moviewithfragments

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviewithfragments.adapters.MovieRecyclerView
import com.example.moviewithfragments.adapters.OnMovieListener
import com.example.moviewithfragments.adapters.RecyclerViewOnScroll
import com.example.moviewithfragments.viewmodels.MovieListViewModel

class SearchActivity : AppCompatActivity(), OnMovieListener {
    private var movieRecyclerAdapter: MovieRecyclerView? = null
    private var recyclerView: RecyclerView? = null
    private var searchView: SearchView? = null
    var movieListViewModel: MovieListViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        movieListViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_search, menu)
        searchView =
            MenuItemCompat.getActionView(menu.findItem(R.id.actionSearch)) as SearchView
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                recyclerView = findViewById(R.id.recyclerView) as RecyclerView
                movieListViewModel?.let { MainActivity().searchMovieApi(query!!, 0, 1, it) }
                movieRecyclerAdapter = MovieRecyclerView(this@SearchActivity)
                recyclerView?.adapter = movieRecyclerAdapter
                recyclerView?.layoutManager =
                    LinearLayoutManager(this@SearchActivity)
                movieListViewModel?.let {
                    RecyclerViewOnScroll().setRecyclerViewScrollListener(
                        it,
                        recyclerView
                    )
                }
                movieListViewModel?.let {
                    ObserveDataChange().observeAnyChange(
                        it, this@SearchActivity,
                        movieRecyclerAdapter!!
                    )
                }
                return false
            }
        })
        return false
    }

    override fun onMovieClick(position: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movie", movieRecyclerAdapter?.getIdOfMovieSelected(position))
        startActivity(intent)
    }

    override fun onCategoryClick(category: String?) {
        TODO("Not yet implemented")
    }

}