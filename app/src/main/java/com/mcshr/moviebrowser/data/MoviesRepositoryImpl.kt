package com.mcshr.moviebrowser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mcshr.moviebrowser.data.local.database.FavoriteMoviesDao
import com.mcshr.moviebrowser.data.remote.TmdbApi
import com.mcshr.moviebrowser.domain.MoviesRepository
import com.mcshr.moviebrowser.domain.entities.Movie
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: TmdbApi,
    private val dao: FavoriteMoviesDao
): MoviesRepository {
    override suspend fun getMovieList(): List<Movie> {
        val favIds = dao.getAllIdsFavoriteMovies()
        return api.getPopularMovies().toDomain(favIds)
    }

    override suspend fun getMovieById(id: Int): Movie {
        val favIds = dao.getAllIdsFavoriteMovies()
        return api.getMovieById(id).toDomain(id in favIds)
    }

    override suspend fun addToFavorites(movie: Movie) {
        dao.addToFavorites(movie.toDbModel())
    }

    override suspend fun removeFromFavorites(movie: Movie) {
        dao.removeFromFavorites(movie.toDbModel())
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>> {
       return dao.getAllFavoriteMovies().map { dbFavList ->
           dbFavList.map { dbFav ->
               dbFav.toDomain()
           }
       }
    }

    override suspend fun isInFavorites(id:Int): Boolean {
        return dao.exists(id)
    }

}