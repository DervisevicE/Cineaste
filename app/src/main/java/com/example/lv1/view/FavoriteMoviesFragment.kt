package com.example.lv1.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lv1.MovieDetailActivity
import com.example.lv1.R
import com.example.lv1.data.Movie
import com.example.lv1.viewmodel.MovieListViewModel
import android.util.Pair as UtilPair

class FavoriteMoviesFragment : Fragment() {

    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter
    private var movieListViewModel =  MovieListViewModel(null,null)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       var view =  inflater.inflate(R.layout.favorites_fragment, container, false)
        favoriteMovies = view.findViewById(R.id.favoriteMovies)
        favoriteMovies.layoutManager = GridLayoutManager(activity, 2)
        favoriteMoviesAdapter = MovieListAdapter(arrayListOf()) { movie,view1,view2 -> showMovieDetails(movie,view1,view2) }
        favoriteMovies.adapter=favoriteMoviesAdapter
        favoriteMoviesAdapter.updateMovies(movieListViewModel.getFavoriteMovies())
        return view;
    }
    companion object {
        fun newInstance(): FavoriteMoviesFragment = FavoriteMoviesFragment()
    }
    private fun showMovieDetails(movie: Movie, view1: View,view2:View) {
        val intent = Intent(activity, MovieDetailActivity::class.java).apply {
            putExtra("movie_title", movie.title)
        }
        val options = ActivityOptions
            .makeSceneTransitionAnimation(activity,  UtilPair.create(view1, "poster"),
                UtilPair.create(view2, "title"))
        startActivity(intent, options.toBundle())
    }
}