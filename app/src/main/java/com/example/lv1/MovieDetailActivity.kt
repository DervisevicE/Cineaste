package com.example.lv1

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.lv1.data.Movie
import com.example.lv1.view.ActorsFragment
import com.example.lv1.view.SimilarFragment
import com.example.lv1.viewmodel.MovieDetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

class MovieDetailActivity : AppCompatActivity() {

    private var movieDetailViewModel =  MovieDetailViewModel(this@MovieDetailActivity::movieRetrieved,null,null)
    private lateinit var bottomNavigation: BottomNavigationView
    private  var movie=Movie(0,"Test","Test","Test","Test","Test","Test")
    private lateinit var title : TextView
    private lateinit var overview : TextView
    private lateinit var releaseDate : TextView
    private lateinit var genre : TextView
    private lateinit var website : TextView
    private lateinit var poster : ImageView
    private lateinit var backdrop : ImageView
    private lateinit var shareButton : FloatingActionButton
    private val posterPath = "https://image.tmdb.org/t/p/w780"
    private val backdropPath = "https://image.tmdb.org/t/p/w500"

    private val mOnItemSelectedListener = NavigationBarView.OnItemSelectedListener{ item ->
        when(item.itemId){
            R.id.actorsItem -> {
                var actorsFragment = ActorsFragment(movie.title,movie.id)
                openFragment(actorsFragment)
                return@OnItemSelectedListener true
            }
            R.id.similarMItem -> {
                var similarFragment = SimilarFragment(movie.title,movie.id)
                openFragment(similarFragment)
                return@OnItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        releaseDate = findViewById(R.id.movie_release_date)
        genre = findViewById(R.id.movie_genre)
        poster = findViewById(R.id.movie_poster)
        website = findViewById(R.id.movie_website)
        shareButton = findViewById(R.id.shareButton)
        backdrop = findViewById(R.id.movie_backdrop)

        bottomNavigation = findViewById(R.id.detailNavigation)
        bottomNavigation.setOnItemSelectedListener(mOnItemSelectedListener)

        website.setOnClickListener{
            showWebsite()
        }
        title.setOnClickListener{
            youtubeSearch()
        }
        shareButton.setOnClickListener{
            shareOverview()
        }
        val extras = intent.extras

        if (extras != null) {
            if (extras.containsKey("movie_title")) {
                movie = movieDetailViewModel.getMovieByTitle(extras.getString("movie_title", ""))
                populateDetails()
            }
            else if (extras.containsKey("movie_id")){
                movieDetailViewModel.getMovie(extras.getLong("movie_id"),onSuccess = ::onSuccess,
                    onError = ::onError )
            }
        } else {
            finish()
        }

        bottomNavigation.selectedItemId = R.id.actorsItem
    }
    fun movieRetrieved(movie:Movie){
        this.movie =movie;
        populateDetails()
    }
    private fun populateDetails() {
        title.text=movie.title
        releaseDate.text=movie.releaseDate
        //genre.text=movie.genre
        website.text=movie.homepage
        overview.text=movie.overview
        val context: Context = poster.getContext()
        /*var id = 0;
        if (movie.genre!==null)
            id = context.getResources()
                .getIdentifier(movie.genre, "drawable", context.getPackageName())
        if (id===0) id=context.getResources()
            .getIdentifier("picture1", "drawable", context.getPackageName())*/
        Glide.with(context)
            .load(posterPath + movie.posterPath)
            .placeholder(R.drawable.picture1)
            .error(R.drawable.picture1)
            .fallback(R.drawable.picture1)
            .into(poster);
        var backdropContext: Context = backdrop.getContext()
        Glide.with(backdropContext)
            .load(backdropPath + movie.backdropPath)
            .centerCrop()
            .placeholder(R.drawable.backdrop)
            .error(R.drawable.backdrop)
            .fallback(R.drawable.backdrop)
            .into(backdrop);
    }
    private fun showWebsite(){

        val webIntent: Intent = Uri.parse(movie.homepage).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        try {
            startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }
    }

    private fun youtubeSearch(){
        val intent = Intent(Intent.ACTION_SEARCH).apply {
            setPackage("com.google.android.youtube")
            putExtra("query", movie.title + " trailer")
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }
    }

    private fun shareOverview(){
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, movie.overview)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.actorSimilarContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun onSuccess(movie:Movie){
        this.movie =movie;
        populateDetails()
    }
    fun onError() {
        val toast = Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT)
        toast.show()
    }


}