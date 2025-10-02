package com.mcshr.moviebrowser.data.remote.dto

data class ResponsePopularMoviesDto(
    val page:Int,
    val results:List<MovieDto>
)
