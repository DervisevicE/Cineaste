package com.example.lv1

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.cinaeste.viewmodel.MovieDetailViewModel
import com.example.lv1.data.Movie
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MovieDetailActivity : AppCompatActivity() {

    private var movieDetailViewModel = MovieDetailViewModel()
    private lateinit var movie: Movie
    private lateinit var title: TextView
    private lateinit var overview: TextView
    private lateinit var relaseDate: TextView
    private lateinit var genre: TextView
    private lateinit var website: TextView
    private lateinit var poster: ImageView
    private lateinit var shareButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        relaseDate = findViewById(R.id.movie_release_date)
        genre = findViewById(R.id.movie_genre)
        website = findViewById(R.id.movie_website)
        poster = findViewById(R.id.movie_poster)
        shareButton = findViewById(R.id.shareButton)

        val extras = intent.extras
        if (extras != null){
            movie = movieDetailViewModel.getMovieByTitle(extras.getString("movie_title",""))
            populateDetails()
        }
        else{
            finish()
        }

        website.setOnClickListener{
            showWebsite()
        }

        title.setOnClickListener{
            showYoutube()
        }

        shareButton.setOnClickListener{
            shareOverview()
        }
    }

    private fun shareOverview() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, movie.overview)
            type = "text/plain"
        }
        try {
            startActivity(shareIntent)
        } catch (e: ActivityNotFoundException){
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }
    }

    private fun showYoutube() {
        val ytIntent = Intent().apply {
            action = Intent.ACTION_SEARCH
            setPackage("com.google.android.youtube")
            putExtra("query", movie.title+" trailer")
        }
        try {
            startActivity(ytIntent)
        } catch (e: ActivityNotFoundException){
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }

       /* val ytIntent = Intent(Intent.ACTION_SEARCH)
        ytIntent.setPackage("com.google.adroid.youtube")
        ytIntent.putExtra("query",title.toString() + " trailer")
        try {
            startActivity(ytIntent)
        } catch (e: ActivityNotFoundException){
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }*/
    }

    private fun showWebsite() {
        val webIntent : Intent = Uri.parse(movie.homepage).let { webpage ->
            Intent(Intent.ACTION_VIEW,webpage)
        }
        try {
            startActivity(webIntent)
        } catch (e: ActivityNotFoundException){
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }
    }

    private fun populateDetails() {
        title.text=movie.title
        relaseDate.text=movie.relaseDate
        genre.text=movie.genre
        website.text=movie.homepage
        overview.text=movie.overview

        val context: Context = poster.context
        var id: Int = context.resources
            .getIdentifier(movie.genre,"drawable", context.packageName)
        if(id==0) id=context.resources
            .getIdentifier("picture1","drawable", context.packageName)
        poster.setImageResource(id)
    }

}