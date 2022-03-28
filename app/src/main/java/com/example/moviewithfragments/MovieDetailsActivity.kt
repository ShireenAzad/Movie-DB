package com.example.moviewithfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.moviewithfragments.databinding.ActivityMovieDetailsBinding
import com.example.moviewithfragments.models.MovieModel
import com.example.moviewithfragments.utils.Credentials

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var activityMovieDetailsBinding: ActivityMovieDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieDetailsBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(activityMovieDetailsBinding.root)
        if (intent.hasExtra("movie")) {
            val movieModel = intent.getParcelableExtra<MovieModel>("movie")
            activityMovieDetailsBinding.title.text = movieModel?.title.toString()
            activityMovieDetailsBinding.overview.text = movieModel?.overview.toString()
            if (movieModel != null) {
                activityMovieDetailsBinding.ratingBar.rating = (movieModel.voteAverage) / 2
            }

            Glide.with(this).load(Credentials().IMAGE_URI + movieModel?.posterPath)
                .into(activityMovieDetailsBinding.imageView)
        }

    }

}