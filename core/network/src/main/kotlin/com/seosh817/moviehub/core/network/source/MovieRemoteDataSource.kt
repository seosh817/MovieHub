package com.seosh817.moviehub.core.network.source

import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.MoviesResponse
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkMovieDetail
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse

interface MovieRemoteDataSource {

    suspend fun fetchPopularMovies(page: Int, language: String? = null): ResultState<NetworkMoviesResponse>

    suspend fun fetchTopRatedMovies(page: Int, language: String? = null): ResultState<NetworkMoviesResponse>

    suspend fun fetchUpcomingMovies(page: Int, language: String? = null): ResultState<NetworkMoviesResponse>

    suspend fun fetchMovieDetail(movieId: Long, language: String? = null): ResultState<NetworkMovieDetail>
}
