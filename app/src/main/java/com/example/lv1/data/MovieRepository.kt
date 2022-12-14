package com.example.lv1.data


import com.example.lv1.BuildConfig
import com.example.lv1.data.Movie
import com.example.lv1.data.favoriteMovies
import com.example.lv1.data.recentMovies
import com.example.lv1.data.similarMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import com.example.lv1.data.*


sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

object MovieRepository {

    private const val tmdb_api_key : String = BuildConfig.TMDB_API_KEY
    fun getFavoriteMovies() : List<Movie> {
        return favoriteMovies();
    }

    fun getRecentMovies() : List<Movie> {
        return recentMovies();
    }

    fun getSimilarMovies(): Map<String, List<String>> {
        return similarMovies()
    }

    /*suspend fun getMovieDetails(
        id: Long
    ):Result<Movie>{
        return withContext(Dispatchers.IO) {
            val url1 = "https://api.themoviedb.org/3/movie/$id?api_key=$tmdb_api_key"
            try {
                val url = URL(url1)
                var movie=Movie(0, "Test", "Test", "Test", "Test", "Test", "Test", "Test")
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = JSONObject(result)
                    movie.title = jsonObject.getString("title")
                    movie.id = jsonObject.getLong("id")
                    movie.posterPath = jsonObject.getString("poster_path")
                    movie.overview = jsonObject.getString("overview")
                    movie.releaseDate = jsonObject.getString("release_date")
                    movie.homepage = jsonObject.getString("homepage")
                    movie.backdropPath = jsonObject.getString("backdrop_path")
                    movie.genre = jsonObject.getJSONArray("genres").getJSONObject(0).getString("name")
                }
                return@withContext Result.Success(movie);
            }
            catch (e: MalformedURLException) {
                return@withContext Result.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                return@withContext Result.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                return@withContext Result.Error(Exception("Cannot parse JSON"))
            }
        }
    }*/



    suspend fun getSimilarMoviesAPI(
        id: Long
    ): Result<MutableList<String>> {
        return withContext(Dispatchers.IO) {
            val url1 = "https://api.themoviedb.org/3/movie/$id/similar?api_key=$tmdb_api_key"
            try {
                val url = URL(url1)
                var similar:MutableList<String> = mutableListOf()
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val jo = JSONObject(result)
                    val items: JSONArray = jo.getJSONArray("results")
                    for (i in 0 until items.length()) {
                        val slicni = items.getJSONObject(i)
                        val title = slicni.getString("title")
                        similar.add(title)
                        if (i == 4) break
                    }
                }
                return@withContext Result.Success(similar);
            }
            catch (e: MalformedURLException) {
                return@withContext Result.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                return@withContext Result.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                return@withContext Result.Error(Exception("Cannot parse JSON"))
            }
        }
    }

    suspend fun searchRequest(
        query: String
    ) : GetMoviesResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.searchMovie(query)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getUpcomingMovies() : GetMoviesResponse?{
        return withContext(Dispatchers.IO){
            var response = ApiAdapter.retrofit.getUpcomingMovies()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getMovie(id: Long
    ) : Movie?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getMovie(id)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
}