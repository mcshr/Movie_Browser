package com.mcshr.moviebrowser.domain.interactors

import androidx.lifecycle.LiveData
import com.mcshr.moviebrowser.domain.MoviesRepository
import com.mcshr.moviebrowser.domain.entities.Movie
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): LiveData<List<Movie>>{
        return repository.getFavoriteMovies()
    }
}