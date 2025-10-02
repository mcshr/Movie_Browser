package com.mcshr.moviebrowser.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.moviebrowser.domain.entities.Movie
import com.mcshr.moviebrowser.domain.interactors.GetMoviesUseCase
import com.mcshr.moviebrowser.domain.interactors.ToggleFavoritesUseCase
import com.mcshr.moviebrowser.domain.wrappers.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val toggleFavoritesUseCase: ToggleFavoritesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<NetworkResult<Unit>>()
    val state: LiveData<NetworkResult<Unit>>
        get() = _state

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    fun loadMovies() {
        viewModelScope.launch {
            _state.value = NetworkResult.Loading()
            val result = getMoviesUseCase()
            when (result) {
                is NetworkResult.Success -> {
                    _movies.value = result.data
                    _state.value = NetworkResult.Success(Unit)
                }

                is NetworkResult.Error -> {
                    _state.value = result
                }

                is NetworkResult.Loading -> {}
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            _movies.value = _movies.value?.map {
                if (it.id == movie.id) {
                    it.copy(
                        isFavorite = !it.isFavorite
                    )
                } else {
                    it
                }
            }
            toggleFavoritesUseCase(movie)
        }
    }

}