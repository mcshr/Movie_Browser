package com.mcshr.moviebrowser.presentation.screens.movie_favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.moviebrowser.domain.entities.Movie
import com.mcshr.moviebrowser.domain.interactors.GetFavoriteMoviesUseCase
import com.mcshr.moviebrowser.domain.interactors.ToggleFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFavoritesViewModel @Inject constructor(
    private val toggleFavoritesUseCase: ToggleFavoritesUseCase,
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {
    val movies = getFavoriteMoviesUseCase()
    fun toggleFavoriteMovie(movie: Movie){
        viewModelScope.launch {
            toggleFavoritesUseCase(movie)
        }
    }
}