package com.example.cinaeste.viewmodel

import com.example.lv1.data.Movie
import com.example.lv1.data.MovieRepository

class MovieListViewModel {

    fun getFavoriteMovies():List<Movie>{
        return MovieRepository.getFavoriteMovies();
    }

    fun getRecentMovies():List<Movie>{
        return MovieRepository.getRecentMovies();
    }
}