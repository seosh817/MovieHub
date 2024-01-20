package com.seosh817.moviehub.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.data.model.asExternalModel
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.network.mapper.asExternalModel
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkMovieDetail
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMovieOverview
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse
import com.seosh817.moviehub.core.network.source.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDataSource: MovieRemoteDataSource,
) : MovieRepository {

    override fun fetchPopularMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return createPager(MovieListPagingSource {
            movieDataSource.fetchPopularMovies(it, language)
                .map(NetworkMoviesResponse::asExternalModel)
        })
    }

    override fun fetchTopRatedMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return createPager(
            MovieListPagingSource {
                movieDataSource.fetchTopRatedMovies(it, language)
                    .map(NetworkMoviesResponse::asExternalModel)
            }
        )
    }

    override fun fetchUpcomingMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return createPager(MovieListPagingSource {
            movieDataSource.fetchUpcomingMovies(it, language)
                .map(NetworkMoviesResponse::asExternalModel)
        })
    }

    override suspend fun fetchMovieDetail(
        movieId: Long,
        language: String?
    ): ResultState<MovieDetail> {
        return movieDataSource.fetchMovieDetail(movieId, language)
            .map(NetworkMovieDetail::asExternalModel)
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
