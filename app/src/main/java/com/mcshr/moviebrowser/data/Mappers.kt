package com.mcshr.moviebrowser.data

import com.mcshr.moviebrowser.data.local.database.entities.FavoriteMovieDbModel
import com.mcshr.moviebrowser.data.remote.TmdbApi
import com.mcshr.moviebrowser.data.remote.dto.MovieDto
import com.mcshr.moviebrowser.data.remote.dto.ResponsePopularMoviesDto
import com.mcshr.moviebrowser.domain.entities.Movie


fun MovieDto.toDomain(isFavorite: Boolean): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        fullDescription = overview,
        posterUrl = TmdbApi.IMAGE_BASE_URL + poster_path,
        releaseDate = release_date,
        isFavorite = isFavorite
    )
}

fun ResponsePopularMoviesDto.toDomain(favIds: List<Int>): List<Movie> {
    return results.map { it.toDomain(it.id in favIds) }
}


fun FavoriteMovieDbModel.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        fullDescription = fullDescription,
        posterUrl = posterUrl,
        releaseDate = releaseDate,
        isFavorite = true
    )
}

fun Movie.toDbModel(): FavoriteMovieDbModel {
    return FavoriteMovieDbModel(
        id = id,
        title = title,
        overview = overview,
        fullDescription = fullDescription,
        posterUrl = posterUrl,
        releaseDate = releaseDate
    )
}