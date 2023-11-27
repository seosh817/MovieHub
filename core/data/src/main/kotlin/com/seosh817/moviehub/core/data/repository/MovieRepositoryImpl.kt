package com.seosh817.moviehub.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.seosh817.moviehub.core.data.mapper.toDomain
import com.seosh817.moviehub.core.data.source.MovieDataSource
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.model.Movie
import com.seosh817.moviehub.core.network.paging.MovieEntityListPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val movieDataSource: MovieDataSource,
) : MovieRepository {

    override fun fetchPopularMovies(language: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieEntityListPagingSource {
                    movieDataSource.fetchPopularMovies(it, language)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun fetchTopRatedMovies(language: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieEntityListPagingSource {
                    movieDataSource.fetchTopRatedMovies(it, language)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun fetchUpcomingMovies(language: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieEntityListPagingSource {
                    movieDataSource.fetchUpcomingMovies(it, language)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
