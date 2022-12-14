package com.example.lv1.viewmodel

import android.util.Log
import com.example.lv1.data.ActorMovieRepository
import com.example.lv1.data.Movie
import com.example.lv1.data.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.lv1.data.Result

class MovieDetailViewModel(private val movieRetrieved: ((movies: Movie) -> Unit)?,
                           private val similarRetrieved: ((similar: MutableList<String>) -> Unit)?,
                           private val actorsRetrieved: ((actors: MutableList<String>) -> Unit)?,

                           ) {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getMovieByTitle(name:String):Movie{
        var movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getRecentMovies())
        movies.addAll(MovieRepository.getFavoriteMovies())
        val movie= movies.find { movie -> name.equals(movie.title) }
        return movie?:Movie(0,"Test","Test","Test","Test","Test","Test")
    }

    fun getActorsByTitle(name: String):List<String>{
        return ActorMovieRepository.getActorMovies()?.get(name)?: emptyList()
    }

    fun getSimilarByTitle(name: String):List<String>{
        return MovieRepository.getSimilarMovies()?.get(name)?: emptyList()
    }

    /*fun getMovieDetails(query: Long){
        scope.launch{
            val result = MovieRepository.getMovieDetails(query)
            when (result) {
                is Result.Success<Movie> -> movieRetrieved?.invoke(result.data)
                else-> Log.v("meh","meh")
            }
        }
    }*/

    fun getSimilarMoviesById(query: Long){
        scope.launch{
            val result = MovieRepository.getSimilarMoviesAPI(query)
            when (result) {
                is Result.Success<MutableList<String>> -> similarRetrieved?.invoke(result.data)
                else-> Log.v("meh","meh")
            }
        }
    }

    fun getActorsById(query: Long){
        scope.launch{
            val result = ActorMovieRepository.getActors(query)
            when (result) {
                is Result.Success<MutableList<String>> -> actorsRetrieved?.invoke(result.data)
                else-> Log.v("meh","meh")
            }
        }
    }

    fun getMovie(query: Long, onSuccess: (movies: Movie) -> Unit, onError :() -> Unit){
        scope.launch {
            val result = MovieRepository.getMovie(query)
            when (result) {
                is Movie -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
}