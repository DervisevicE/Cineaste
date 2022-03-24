package com.example.lv1

class MovieListViewModel {
    fun getFavoriteMovies() : List<Movie>{
        return MovieRepositroy.getFavoriteMovies()
    }

    fun getRecentMovies() : List<Movie>{
        return MovieRepositroy.getRecentMovies()
    }
}