package com.seosh817.moviehub.core.domain.repository

import androidx.paging.PagingData
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.MovieOverview
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun fetchPopularMovies(language: String?): Flow<PagingData<MovieOverview>>

    fun fetchTopRatedMovies(language: String?): Flow<PagingData<MovieOverview>>

    fun fetchUpcomingMovies(language: String?): Flow<PagingData<MovieOverview>>

    suspend fun fetchMovieDetail(movieId: Long, language: String?): ResultState<MovieDetail>

    suspend fun fetchMovieCredtis(movieId: Long, language: String?): ResultState<Credits>
}
