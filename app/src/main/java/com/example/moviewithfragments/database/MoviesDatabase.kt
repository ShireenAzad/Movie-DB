package com.example.moviewithfragments.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviewithfragments.dao.MoviesDao
import com.example.moviewithfragments.model.Movies
import com.example.moviewithfragments.typeconverters.DateConverter

@TypeConverters(DateConverter::class)
@Database(entities = [Movies::class], version = 3, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null
        fun getDatabase(context: Context): MoviesDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MoviesDatabase::class.java,
                        "moviesDB"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}
