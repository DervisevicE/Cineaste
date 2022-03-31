package com.example.lv1

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lv1.data.Movie
import com.example.lv1.view.MovieListAdapter
import com.example.lv1.viewmodel.MovieListViewModel
import com.example.lv1.viewmodel.MyBroadcastReceiver

class MainActivity : AppCompatActivity() {
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter

    private lateinit var upcomingMovies : RecyclerView
    private lateinit var upcomingMoviesAdapter: MovieListAdapter

    private lateinit var searchText: EditText

    private val myBroadcastReceiver : BroadcastReceiver = MyBroadcastReceiver()


    private var movieListViewModel =  MovieListViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        favoriteMovies = findViewById(R.id.favoriteMovies)
        searchText = findViewById(R.id.searchText)
        favoriteMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        favoriteMoviesAdapter = MovieListAdapter(arrayListOf()){movie -> showMovieDetails(movie)}
        favoriteMovies.adapter = favoriteMoviesAdapter
        favoriteMoviesAdapter.updateMovies(movieListViewModel.getFavoriteMovies())

        upcomingMovies = findViewById(R.id.upcomingMovies)
        upcomingMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        upcomingMoviesAdapter = MovieListAdapter(arrayListOf()) {movie -> showMovieDetails(movie)}
        upcomingMovies.adapter = upcomingMoviesAdapter
        upcomingMoviesAdapter.updateMovies(movieListViewModel.getRecentMovies())

        if(intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain")
            handleSendText(intent)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(myBroadcastReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    override fun onPause() {
        unregisterReceiver(myBroadcastReceiver)
        super.onPause()
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            searchText.setText(it)
        }
    }
    private fun showMovieDetails(movie: Movie){
        val intent = Intent(this, MovieDetailActivity::class.java).apply{
            putExtra("movie_title", movie.title)
        }
        startActivity(intent)
    }


}