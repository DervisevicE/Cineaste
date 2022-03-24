package com.example.lv1.viewmodel

import com.example.lv1.data.Movie
import com.example.lv1.data.MovieRepositroy

class MovieListViewModel {
    fun getFavoriteMovies() : List<Movie>{
        return MovieRepositroy.getFavoriteMovies()
    }

    fun getRecentMovies() : List<Movie>{
        return MovieRepositroy.getRecentMovies()
    }
}