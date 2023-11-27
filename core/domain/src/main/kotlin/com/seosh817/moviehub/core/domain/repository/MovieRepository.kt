package com.seosh817.moviehub.core.domain.repository

import androidx.paging.PagingData
import com.seosh817.moviehub.core.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun fetchPopularMovies(language: String): Flow<PagingData<Movie>>

    fun fetchTopRatedMovies(language: String): Flow<PagingData<Movie>>

    fun fetchUpcomingMovies(language: String): Flow<PagingData<Movie>>
}
