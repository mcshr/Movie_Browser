package com.mcshr.moviebrowser.domain.interactors

import coil3.network.HttpException
import com.mcshr.moviebrowser.domain.MoviesRepository
import com.mcshr.moviebrowser.domain.entities.Movie
import com.mcshr.moviebrowser.domain.wrappers.DomainError
import com.mcshr.moviebrowser.domain.wrappers.NetworkResult
import okio.IOException
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(id:Int): NetworkResult<Movie>{
        return try {
            val movie = repository.getMovieById(id)
            NetworkResult.Success(movie)
        } catch (_: IOException) {
            NetworkResult.Error(DomainError.NetworkError())
        } catch (_: HttpException) {
            NetworkResult.Error(DomainError.ServerError())
        } catch (e: Exception) {
            NetworkResult.Error(DomainError.UnknownError(e))
        }
    }
}