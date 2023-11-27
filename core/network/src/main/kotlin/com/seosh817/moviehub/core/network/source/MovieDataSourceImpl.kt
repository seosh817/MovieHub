package com.seosh817.moviehub.core.network.source

import com.seosh817.common.network.handleApi
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.data.source.MovieDataSource
import com.seosh817.moviehub.core.network.model.movie_list.MovieResponseEntity
import com.seosh817.moviehub.core.network.service.movie_list.MovieService
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieDataSource {

    override suspend fun fetchPopularMovies(page: Int, language: String): ResultState<MovieResponseEntity> {
        return handleApi {
            movieService.fetchPopularMovies(page, language)
        }
    }

    override suspend fun fetchTopRatedMovies(page: Int, language: String): ResultState<MovieResponseEntity> {
        return handleApi {
            movieService.fetchTopRatedMovies(page, language)
        }
    }

    override suspend fun fetchUpcomingMovies(page: Int, language: String): ResultState<MovieResponseEntity> {
        return handleApi {
            movieService.fetchUpcomingMovies(page, language)
        }
    }
}
