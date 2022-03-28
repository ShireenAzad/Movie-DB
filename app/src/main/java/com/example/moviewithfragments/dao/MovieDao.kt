package com.example.moviewithfragments.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviewithfragments.models.MovieModel
import com.example.moviewithfragments.models.MovieModelRoom


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(movieViewModel:MovieModel)
    @Query("select * from MovieDB where primaryYear= :year")
    suspend fun getMoviesByCurrentYear(year:Int):List<MovieModelRoom>
}