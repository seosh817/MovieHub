package com.seosh817.moviehub.core.network.source

import com.seosh817.common.network.handleApi
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.data.source.MovieDataSource
import com.seosh817.moviehub.core.model.MovieResponse
import com.seosh817.moviehub.core.network.mapper.asExternalModel
import com.seosh817.moviehub.core.network.service.movie_list.MovieService
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieDataSource {

    override suspend fun fetchPopularMovies(page: Int, language: String?): ResultState<MovieResponse> {
        return handleApi {
            movieService.fetchPopularMovies(page, language)
        }
            .map {
                it.asExternalModel()
            }
    }

    override suspend fun fetchTopRatedMovies(page: Int, language: String?): ResultState<MovieResponse> {
        return handleApi {
            movieService.fetchTopRatedMovies(page, language)
        }
            .map {
                it.asExternalModel()
            }
    }

    override suspend fun fetchUpcomingMovies(page: Int, language: String?): ResultState<MovieResponse> {
        return handleApi {
            movieService.fetchUpcomingMovies(page, language)
        }
            .map {
                it.asExternalModel()
            }
    }
}
