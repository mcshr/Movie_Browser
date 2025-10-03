package com.mcshr.moviebrowser.domain.interactors

import com.mcshr.moviebrowser.domain.MoviesRepository
import com.mcshr.moviebrowser.domain.entities.Movie
import javax.inject.Inject

class ToggleFavoritesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie): Boolean {
        val isCurrentlyFavorite = repository.isInFavorites(movie.id)
        if (isCurrentlyFavorite) {
            repository.removeFromFavorites(movie)
            return false
        } else {
            repository.addToFavorites(movie)
            return true
        }
    }
}