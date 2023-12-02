package com.seosh817.moviehub.core.data.source

import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.model.MovieResponse

interface MovieDataSource {

    suspend fun fetchPopularMovies(
        page: Int,
        language: String
    ): ResultState<MovieResponse>

    suspend fun fetchTopRatedMovies(
        page: Int,
        language: String
    ): ResultState<MovieResponse>

    suspend fun fetchUpcomingMovies(
        page: Int,
        language: String
    ): ResultState<MovieResponse>
}
