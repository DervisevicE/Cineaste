package com.example.lv1.data

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val relaseDate: String,
    val homepage: String?,
    val genre: String?,
    val posterPath: String
)