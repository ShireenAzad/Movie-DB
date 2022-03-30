package com.example.moviewithfragments.activities


import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.viewmodel.MovieViewModelFactory
import com.example.moviewithfragments.R
import com.example.moviewithfragments.adapters.MovieRecyclerView
import com.example.moviewithfragments.adapters.OnMovieListener
import com.example.moviewithfragments.api.MovieApi
import com.example.moviewithfragments.database.MoviesDatabase
import com.example.moviewithfragments.repository.MovieRepository
import com.example.moviewithfragments.viewmodel.MovieViewModel


class SearchActivity : AppCompatActivity(), OnMovieListener {
    val movieRecyclerAdapter = MovieRecyclerView(this)
    private var searchView: SearchView? = null
    var moviesViewModel: MovieViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        moviesViewModel =
            ViewModelProvider(
                this,
                MovieViewModelFactory(
                    MovieRepository(
                        MovieApi.create(),
                    ), MoviesDatabase.getDatabase(this)

                )
            ).get(
                MovieViewModel::class.java
            )
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
                if (query != null) {
                    moviesViewModel?.getMoviesByKeyWord(query)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

                recyclerView.adapter = movieRecyclerAdapter
                recyclerView.layoutManager =
                    LinearLayoutManager(this@SearchActivity)
                moviesViewModel?.movies?.observe(this@SearchActivity, Observer {
                    movieRecyclerAdapter?.setMoviesList(it)
                })
                return false

            }

        })
        return false
    }

    override fun onMovieClick(position: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        //intent.putExtra(MOVIE, movieRecyclerAdapter?.getIdOfMovieSelected(position))
        startActivity(intent)
    }



}