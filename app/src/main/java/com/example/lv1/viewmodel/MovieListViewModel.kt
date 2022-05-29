package com.example.lv1.viewmodel

import com.example.lv1.data.GetMoviesResponse
import com.example.lv1.data.Movie
import com.example.lv1.data.MovieRepository
import com.example.lv1.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieListViewModel(private val searchDone: ((movies: List<Movie>) -> Unit)?,
                         private val onError: (()->Unit)?
) {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getFavoriteMovies():List<Movie>{
        return MovieRepository.getFavoriteMovies();
    }

    fun getRecentMovies():List<Movie>{
        return MovieRepository.getRecentMovies();
    }

    fun search(query: String,onSuccess: (movies: List<Movie>) -> Unit,
               onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{

            // Make the network call and suspend execution until it finishes
            val result = MovieRepository.searchRequest(query)

            // Display result of the network request to the user
            when (result) {
                is GetMoviesResponse -> onSuccess?.invoke(result.movies)
                else-> onError?.invoke()
            }
        }
    }

    fun getUpcoming(onSuccess: (movies: List<Movie>) -> Unit, onError: (() -> Unit)){
        scope.launch {
            val result = MovieRepository.getUpcomingMovies()

            when(result){
                is GetMoviesResponse -> onSuccess?.invoke(result.movies)
                else -> onError?.invoke()
            }
        }
    }
}