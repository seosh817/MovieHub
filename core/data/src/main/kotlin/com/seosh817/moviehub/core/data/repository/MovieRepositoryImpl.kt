package com.seosh817.moviehub.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.seosh817.moviehub.core.data.paging.MovieListPagingSource
import com.seosh817.moviehub.core.data.source.MovieDataSource
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.model.MovieOverview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDataSource: MovieDataSource,
) : MovieRepository {

    override fun fetchPopularMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return createPager(MovieListPagingSource {
            movieDataSource.fetchPopularMovies(it, language)
        })
    }

    override fun fetchTopRatedMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return createPager(
            MovieListPagingSource {
                movieDataSource.fetchTopRatedMovies(it, language)
            }
        )
    }

    override fun fetchUpcomingMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return createPager(MovieListPagingSource {
            movieDataSource.fetchUpcomingMovies(it, language)
        })
    }

    private fun createPager(movieListPagingSource: MovieListPagingSource): Flow<PagingData<MovieOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { movieListPagingSource }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
