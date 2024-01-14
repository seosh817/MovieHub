package com.seosh817.moviehub.core.network.source

import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.MoviesResponse

interface MovieRemoteDataSource {

    suspend fun fetchPopularMovies(page: Int, language: String?): ResultState<MoviesResponse>

    suspend fun fetchTopRatedMovies(page: Int, language: String?): ResultState<MoviesResponse>

    suspend fun fetchUpcomingMovies(page: Int, language: String?): ResultState<MoviesResponse>

    suspend fun fetchMovieDetail(movieId: Long, language: String?): ResultState<MovieDetail>
}
