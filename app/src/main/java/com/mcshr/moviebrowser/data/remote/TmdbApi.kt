package com.mcshr.moviebrowser.data.remote

import com.mcshr.moviebrowser.data.remote.dto.MovieDto
import com.mcshr.moviebrowser.data.remote.dto.ResponsePopularMoviesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("language")language: String = DEFAULT_LANG,
        @Query("page")page:Int = DEFAULT_PAGE
    ): ResponsePopularMoviesDto

    @GET("3/movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId:Int,
        @Query("language")language: String = DEFAULT_LANG
    ): MovieDto


    companion object{
        private const val DEFAULT_LANG = "en_US"
        private const val DEFAULT_PAGE = 1
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    }
}