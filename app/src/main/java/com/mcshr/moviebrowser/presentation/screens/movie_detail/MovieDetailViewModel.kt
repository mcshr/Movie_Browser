package com.mcshr.moviebrowser.presentation.screens.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.moviebrowser.domain.entities.Movie
import com.mcshr.moviebrowser.domain.interactors.GetMovieByIdUseCase
import com.mcshr.moviebrowser.domain.interactors.ToggleFavoritesUseCase
import com.mcshr.moviebrowser.domain.wrappers.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val toggleFavoritesUseCase: ToggleFavoritesUseCase
) : ViewModel() {
    val movieId: Int = savedStateHandle["movie_id"] ?: -1
    private val _state = MutableLiveData<NetworkResult<Unit>>()
    val state: LiveData<NetworkResult<Unit>>
        get() = _state

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    fun loadMovie() {
        viewModelScope.launch {
            _state.value = NetworkResult.Loading()
            val result = getMovieByIdUseCase(movieId)
            when (result) {
                is NetworkResult.Success -> {
                    _movie.value = result.data
                    _state.value = NetworkResult.Success(Unit)
                }

                is NetworkResult.Error -> {
                    _state.value = result
                }

                is NetworkResult.Loading -> {}
            }
        }
    }

    private var isProcessing = false
    fun toggleFavorite() {
        if (isProcessing) return
        viewModelScope.launch {
            isProcessing = true
            val movie = movie.value
            movie?.let{
                val newState = toggleFavoritesUseCase(movie)
                _movie.value = _movie.value?.let{
                    if (it.id == movie.id) it.copy(isFavorite = newState) else it
                }
            }
            isProcessing = false
        }
    }

}