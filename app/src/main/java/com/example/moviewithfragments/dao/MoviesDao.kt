package com.example.moviewithfragments.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviewithfragments.model.Movies

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movies>)

    @Query("SELECT * FROM movies")
    fun getMovies(): List<Movies>

    @Query("SELECT * FROM movies where cast(substr(releaseDate,1,4) as integer)=:year")
    fun getCurrentYearMovies(year:Int): List<Movies>


}
