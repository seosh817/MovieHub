package com.seosh817.moviehub.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.data.paging.MovieListPagingSource
import com.seosh817.moviehub.core.data.source.MovieDataSource
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.model.MovieDetail
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

    override suspend fun fetchMovieDetail(movieId: Long, language: String?): ResultState<MovieDetail> {
        return movieDataSource.fetchMovieDetail(movieId, language)
    }

    override suspend fun fetchMovieCredtis(movieId: Long, language: String?): ResultState<Credits> {
        return movieDataSource.fetchMovieCredits(movieId, language)
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
