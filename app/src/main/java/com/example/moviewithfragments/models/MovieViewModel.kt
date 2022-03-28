package com.example.moviewithfragments.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.moviewithfragments.database.MoviesDatabase.Companion.getDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    fun insertData(model: MovieModel){
        GlobalScope.launch {
            getDatabase(getApplication<Application>().applicationContext).movieDao().insertData(model)
        }
    }
}