/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seosh817.moviehub.core.network.impl

import com.seosh817.common.network.handleApi
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkMovieDetail
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse
import com.seosh817.moviehub.core.network.service.movie.MovieService
import com.seosh817.moviehub.core.network.source.MoviesRemoteDataSource
import javax.inject.Inject

class MoviesDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
) : MoviesRemoteDataSource {

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
