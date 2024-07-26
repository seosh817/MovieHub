package com.seosh817.moviehub.core.domain.repository

import androidx.paging.PagingData
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.model.MovieType
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun fetchPopularMovies(language: String? = null): Flow<PagingData<MovieOverview>>

    fun fetchTopRatedMovies(language: String? = null): Flow<PagingData<MovieOverview>>

    fun fetchUpcomingMovies(language: String? = null): Flow<PagingData<MovieOverview>>

    fun fetchFavoritesMovies(language: String? = null): Flow<PagingData<MovieOverview>>

    suspend fun fetchMovieDetail(movieId: Long, language: String? = null): ResultState<MovieDetail>

    suspend fun getMovieById(movieType: MovieType, id: Long): MovieOverview?
}
