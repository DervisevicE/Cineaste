package com.example.lv1

import com.example.lv1.data.Movie
import org.hamcrest.CoreMatchers.`is` as Is
import com.example.lv1.data.MovieRepository
import junit.framework.Assert.assertEquals
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Test

class RepositoryUnitTest {
    @Test
    fun testGetFavoriteMovies(){
        val movies = MovieRepository.getFavoriteMovies()
        assertEquals(movies.size,6)
        assertThat(movies, hasItem<Movie>(hasProperty("title", Is("Black Widow"))))
        assertThat(movies, not(hasItem<Movie>(hasProperty("title", Is("Pulp Fiction")))))
    }

    @Test
    fun testGetRecentMovies(){
        val movies = MovieRepository.getRecentMovies()
        assertEquals(movies.size,5)
        assertThat(movies, hasItem<Movie>(hasProperty("title", Is("Love tactics"))))
        assertThat(movies, not(hasItem<Movie>(hasProperty("title", Is("Black Widow")))))
    }
}