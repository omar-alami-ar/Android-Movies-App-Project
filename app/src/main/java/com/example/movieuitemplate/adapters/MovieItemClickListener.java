package com.example.movieuitemplate.adapters;

import android.widget.ImageView;

import com.example.movieuitemplate.models.Movie;

public interface MovieItemClickListener {

    void onMovieClick(Movie movie, ImageView movieImageView); // we will need the ImageView for the animation
}
