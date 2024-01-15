package com.seosh817.moviehub.core.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.data.model.asEntity
import com.seosh817.moviehub.core.database.AppDatabase
import com.seosh817.moviehub.core.database.dao.MovieDao
import com.seosh817.moviehub.core.database.dao.RemoteKeyDao
import com.seosh817.moviehub.core.database.model.MovieEntity
import com.seosh817.moviehub.core.database.model.RemoteKey
import com.seosh817.moviehub.core.model.MovieListType
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMovieOverview
import com.seosh817.moviehub.core.network.source.MovieRemoteDataSource
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieListPagingMediator @Inject constructor(
    private val database: AppDatabase,
    private val remoteSource: MovieRemoteDataSource,
    private val type: MovieListType
) : RemoteMediator<Int, MovieEntity>() {
    private val movieDao: MovieDao = database.movieDao()
    private val remoteKeyDao: RemoteKeyDao = database.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.

            Log.d("!!!", "mediator loadType: ${loadType}, state: ${state}")

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                    Log.d("!!!", "mediator prepend remoteKey: ${remoteKeys != null}")
                    prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    Log.d("!!!", "mediator append remoteKey: ${remoteKeys != null}")
                    nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            Log.d("!!!", "mediator loadkey: ${page}")

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.

            val response = when (type) {
                MovieListType.POPULAR -> {
                    remoteSource.fetchPopularMovies(page = page.toInt())
                }

                MovieListType.TOP_RATED -> {
                    remoteSource.fetchTopRatedMovies(page = page.toInt())
                }

                MovieListType.UPCOMING -> {
                    remoteSource.fetchUpcomingMovies(page = page.toInt())
                }
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearAll()
                    remoteKeyDao.clearAll()
                }

                when (response) {
                    is ResultState.Success -> {
                        val movies = response.data.results
                        val endOfPaginationReached = movies.isEmpty()
                        val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                        val nextKey = if (endOfPaginationReached) null else page + 1

                        val remoteKeys = movies.map {
                            RemoteKey(
                                label = type.value,
                                movieId = it.id,
                                prevKey = prevKey,
                                nextKey = nextKey
                            )
                        }

                        remoteKeyDao.insertOrReplaceAll(remoteKeys)
                        movieDao.insertOrReplaceAll(movies.map(NetworkMovieOverview::asEntity))

                        MediatorResult.Success(
                            endOfPaginationReached = endOfPaginationReached
                        )
                    }

                    is ResultState.Failure<*> -> {
                        MediatorResult.Error(response.e)
                    }
                }
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeyDao().getRemoteKeyByMovieID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKey? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            database.remoteKeyDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            database.remoteKeyDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX: Long = 1
    }
}
