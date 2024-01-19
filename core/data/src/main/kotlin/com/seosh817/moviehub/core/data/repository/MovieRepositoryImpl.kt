package com.seosh817.moviehub.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.data.paging.MovieListPagingMediator
import com.seosh817.moviehub.core.database.AppDatabase
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.data.model.asExternalModel
import com.seosh817.moviehub.core.database.mapper.asExternalModel
import com.seosh817.moviehub.core.database.model.MovieEntity
import com.seosh817.moviehub.core.model.MovieListType
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkMovieDetail
import com.seosh817.moviehub.core.network.service.movie.MovieService
import com.seosh817.moviehub.core.network.source.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieHubDatabase: AppDatabase,
    private val movieDataSource: MovieRemoteDataSource,
    private val movieApiService: MovieService
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun fetchPopularMovies(language: String?): Flow<PagingData<MovieOverview>> {
//        return createPager(
//            pagingSource = movieHubDatabase.movieDao().pagingSource(),
//            movieListPagingMediator = MovieListPagingMediator(
//                moviesDatabase = movieHubDatabase,
//                moviesApiService = movieApiService,
//                type = MovieListType.POPULAR,
//            )
//        )

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 5,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { movieHubDatabase.movieDao().pagingSource() },
            remoteMediator = MovieListPagingMediator(
                moviesDatabase = movieHubDatabase,
                moviesApiService = movieApiService,
                type = MovieListType.POPULAR,
            )
        ).flow
            .map { pagingData ->
                pagingData.map { movieEntity ->
                    movieEntity.asExternalModel()
                }
            }
    }

    override fun fetchTopRatedMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return createPager(
            pagingSource = movieHubDatabase.movieDao().pagingSource(),
            movieListPagingMediator = MovieListPagingMediator(
                moviesDatabase = movieHubDatabase,
                moviesApiService = movieApiService,
                type = MovieListType.TOP_RATED,
            )
        )
    }

    override fun fetchUpcomingMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return createPager(
            pagingSource = movieHubDatabase.movieDao().pagingSource(),
            movieListPagingMediator = MovieListPagingMediator(
                moviesDatabase = movieHubDatabase,
                moviesApiService = movieApiService,
                type = MovieListType.UPCOMING,
            )
        )
    }

    override suspend fun fetchMovieDetail(
        movieId: Long,
        language: String?
    ): ResultState<MovieDetail> {
        return movieDataSource.fetchMovieDetail(movieId, language)
            .map(NetworkMovieDetail::asExternalModel)
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun createPager(
        pagingSource: PagingSource<Int, MovieEntity>,
        movieListPagingMediator: MovieListPagingMediator
    ): Flow<PagingData<MovieOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { pagingSource },
            remoteMediator = movieListPagingMediator
        ).flow
            .map { pagingData ->
                pagingData.map { movieEntity ->
                    movieEntity.asExternalModel()
                }
            }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
