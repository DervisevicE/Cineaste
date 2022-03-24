package com.example.lv1

object MovieRepositroy {
    fun getFavoriteMovies() : List<Movie>{
        return favoriteMovies()
    }

    fun getRecentMovies() : List<Movie>{
        return recentMovies()
    }
}