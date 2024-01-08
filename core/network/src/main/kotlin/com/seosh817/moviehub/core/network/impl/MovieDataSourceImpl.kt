package com.seosh817.moviehub.core.network.impl

import com.seosh817.common.network.handleApi
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.MoviesResponse
import com.seosh817.moviehub.core.network.mapper.asExternalModel
import com.seosh817.moviehub.core.network.service.movie.MovieService
import com.seosh817.moviehub.core.network.source.MovieRemoteDataSource
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRemoteDataSource {

    override suspend fun fetchPopularMovies(page: Int, language: String?): ResultState<MoviesResponse> {
        return handleApi {
            movieService.fetchPopularMovies(page, language)
        }
            .map {
                it.asExternalModel()
            }
    }

    override suspend fun fetchTopRatedMovies(page: Int, language: String?): ResultState<MoviesResponse> {
        return handleApi {
            movieService.fetchTopRatedMovies(page, language)
        }
            .map {
                it.asExternalModel()
            }
    }

    override suspend fun fetchUpcomingMovies(page: Int, language: String?): ResultState<MoviesResponse> {
        return handleApi {
            movieService.fetchUpcomingMovies(page, language)
        }
            .map {
                it.asExternalModel()
            }
    }

    override suspend fun fetchMovieDetail(movieId: Long, language: String?): ResultState<MovieDetail> {
        return handleApi {
            movieService.fetchMovieDetail(movieId, language)
        }
            .map {
                it.asExternalModel()
            }
    }

    override suspend fun fetchMovieCredits(movieId: Long, language: String?): ResultState<Credits> {
        return handleApi {
            movieService.fetchMovieCredits(movieId, language)
        }
            .map {
                it.asExternalModel()
            }
    }
}
