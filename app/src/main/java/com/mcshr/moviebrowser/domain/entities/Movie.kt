package com.mcshr.moviebrowser.domain.entities

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val fullDescription: String,
    val posterUrl: String,
    val releaseDate: String,
    val isFavorite: Boolean
)

