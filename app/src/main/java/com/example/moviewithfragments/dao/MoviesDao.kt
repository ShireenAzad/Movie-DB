package com.example.moviewithfragments.dao

import androidx.room.*
import com.example.moviewithfragments.model.Movies

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movies>)

    @Query("SELECT * FROM movies")
    fun getMovies(): List<Movies>

}
