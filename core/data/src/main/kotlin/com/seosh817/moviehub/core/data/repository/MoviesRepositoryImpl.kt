package com.seosh817.moviehub.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.data.model.asExternalModel
import com.seosh817.moviehub.core.data.paging.MoviesRemoteMediator
import com.seosh817.moviehub.core.database.MovieHubDatabase
import com.seosh817.moviehub.core.database.model.FavoriteEntity
import com.seosh817.moviehub.core.database.model.MovieEntity
import com.seosh817.moviehub.core.database.model.asExternalModel
import com.seosh817.moviehub.core.domain.repository.MoviesRepository
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkMovieDetail
import com.seosh817.moviehub.core.network.source.MoviesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesRepositoryImpl @Inject constructor(
    private val movieHubDatabase: MovieHubDatabase,
    private val moviesDataSource: MoviesRemoteDataSource,
) : MoviesRepository {

    override fun fetchPopularMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { movieHubDatabase.movieDao().pagingSource(MovieType.POPULAR) },
            remoteMediator = MoviesRemoteMediator(
                moviesDatabase = movieHubDatabase,
                remoteSource = moviesDataSource,
                type = MovieType.POPULAR,
            )
        ).flow
            .map { pagingData ->
                pagingData.map(MovieEntity::asExternalModel)
            }
    }

    override fun fetchTopRatedMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { movieHubDatabase.movieDao().pagingSource(MovieType.TOP_RATED) },
            remoteMediator = MoviesRemoteMediator(
                moviesDatabase = movieHubDatabase,
                remoteSource = moviesDataSource,
                type = MovieType.TOP_RATED,
            )
        ).flow
            .map { pagingData ->
                pagingData.map(MovieEntity::asExternalModel)
            }
    }

    override fun fetchUpcomingMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { movieHubDatabase.movieDao().pagingSource(MovieType.UPCOMING) },
            remoteMediator = MoviesRemoteMediator(
                moviesDatabase = movieHubDatabase,
                remoteSource = moviesDataSource,
                type = MovieType.UPCOMING,
            )
        ).flow
            .map { pagingData ->
                pagingData.map(MovieEntity::asExternalModel)
            }
    }

    override fun fetchFavoritesMovies(language: String?): Flow<PagingData<MovieOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { movieHubDatabase.favoritesDao().pagingSource() },
        ).flow
            .map { pagingData ->
                pagingData.map(FavoriteEntity::asExternalModel)
            }
    }

    override suspend fun fetchMovieDetail(
        movieId: Long,
        language: String?
    ): ResultState<MovieDetail> {
        return moviesDataSource.fetchMovieDetail(movieId, language)
            .map(NetworkMovieDetail::asExternalModel)
    }

    override suspend fun getMovieById(movieType: MovieType, id: Long): MovieOverview? {
        return movieHubDatabase
            .movieDao()
            .getMovieById(movieType, id)
            ?.asExternalModel()
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
