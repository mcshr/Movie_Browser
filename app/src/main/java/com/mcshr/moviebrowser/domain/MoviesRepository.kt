package com.mcshr.moviebrowser.domain

import androidx.lifecycle.LiveData
import com.mcshr.moviebrowser.domain.entities.Movie

interface MoviesRepository {
    //remote
    suspend fun getMovieList():List<Movie>
    suspend fun getMovieById(id:Int): Movie
    //local
    suspend fun addToFavorites(movie: Movie)
    suspend fun removeFromFavorites(movie: Movie)
    fun getFavoriteMovies(): LiveData<List<Movie>>
    suspend fun isInFavorites(id: Int): Boolean
}