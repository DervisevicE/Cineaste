package com.example.lv1.viewmodel

import com.example.lv1.data.Movie
import com.example.lv1.data.MovieRepositroy

class MovieDetailViewModel {
    fun getMovieByTitle(name: String) : Movie {
        var movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepositroy.getRecentMovies())
        movies.addAll(MovieRepositroy.getFavoriteMovies())

        val movie=movies.find { movie -> name.equals(movie.title) }
        return movie?:Movie(0,"Test","Test","Test","Test","Test")
    }
}