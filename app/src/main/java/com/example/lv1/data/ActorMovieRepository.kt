package com.example.cinaeste.data

import com.example.lv1.data.movieActors

object ActorMovieRepository {
    fun getActorMovies():Map<String,List<String>>{
        return movieActors()
    }
}
