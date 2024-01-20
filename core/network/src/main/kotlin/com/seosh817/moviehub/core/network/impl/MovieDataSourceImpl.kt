package com.seosh817.moviehub.core.network.impl

import com.seosh817.common.network.handleApi
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkMovieDetail
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse
import com.seosh817.moviehub.core.network.service.movie.MovieService
import com.seosh817.moviehub.core.network.source.MovieRemoteDataSource
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRemoteDataSource {

    override suspend fun fetchPopularMovies(page: Int, language: String?): ResultState<NetworkMoviesResponse> {
        return handleApi {
            movieService.fetchPopularMovies(page, language)
        }
    }

    override suspend fun fetchTopRatedMovies(page: Int, language: String?): ResultState<NetworkMoviesResponse> {
        return handleApi {
            movieService.fetchTopRatedMovies(page, language)
        }
    }

    override suspend fun fetchUpcomingMovies(page: Int, language: String?): ResultState<NetworkMoviesResponse> {
        return handleApi {
            movieService.fetchUpcomingMovies(page, language)
        }
    }

    override suspend fun fetchMovieDetail(movieId: Long, language: String?): ResultState<NetworkMovieDetail> {
        return handleApi {
            movieService.fetchMovieDetail(movieId, language)
        }
    }
}
