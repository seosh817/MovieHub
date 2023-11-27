package com.seosh817.moviehub.core.data.source

import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.network.model.movie_list.MovieResponseEntity

interface MovieDataSource {

    suspend fun fetchPopularMovies(
        page: Int,
        language: String
    ): ResultState<MovieResponseEntity>

    suspend fun fetchTopRatedMovies(
        page: Int,
        language: String
    ): ResultState<MovieResponseEntity>

    suspend fun fetchUpcomingMovies(
        page: Int,
        language: String
    ): ResultState<MovieResponseEntity>
}
