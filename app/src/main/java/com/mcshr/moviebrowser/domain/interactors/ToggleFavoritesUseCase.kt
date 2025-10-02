package com.mcshr.moviebrowser.domain.interactors

import com.mcshr.moviebrowser.domain.MoviesRepository
import com.mcshr.moviebrowser.domain.entities.Movie
import javax.inject.Inject

class ToggleFavoritesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie) {
        if (movie.isFavorite) {
            repository.removeFromFavorites(movie)
        } else {
            repository.addToFavorites(movie)
        }
    }
}