package com.example.lv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lv1.view.MovieListAdapter
import com.example.lv1.viewmodel.MovieListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter

    private lateinit var recentMovies : RecyclerView
    private lateinit var recentMoviesAdapter: MovieListAdapter

    private var movieListViewModel =  MovieListViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        favoriteMovies = findViewById(R.id.favoriteMovies)
        favoriteMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        favoriteMoviesAdapter = MovieListAdapter(listOf())
        favoriteMovies.adapter = favoriteMoviesAdapter
        favoriteMoviesAdapter.updateMovies(movieListViewModel.getFavoriteMovies())

        recentMovies = findViewById(R.id.recentMovies)
        recentMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recentMoviesAdapter = MovieListAdapter(listOf())
        recentMovies.adapter = recentMoviesAdapter
        recentMoviesAdapter.updateMovies(movieListViewModel.getRecentMovies())
    }
}