package com.seosh817.moviehub.core.network.fake

import JvmUnitTestFakeAssetManager
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkMovieDetail
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse
import com.seosh817.moviehub.core.network.source.MoviesRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
class FakeMoviesDataSource @Inject constructor(
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val json: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(page: Int, language: String?): ResultState<NetworkMoviesResponse> =
        withContext<NetworkMoviesResponse>(dispatcher) {
            assets.open(POPULAR_MOVIES_ASSET).use(json::decodeFromStream)
        }.let {
            ResultState.Success(it)
        }

    override suspend fun fetchTopRatedMovies(page: Int, language: String?): ResultState<NetworkMoviesResponse> =
        withContext<NetworkMoviesResponse>(dispatcher) {
            assets.open(TOP_RATED_MOVIES_ASSET).use(json::decodeFromStream)
        }.let {
            ResultState.Success(it)
        }

    override suspend fun fetchUpcomingMovies(page: Int, language: String?): ResultState<NetworkMoviesResponse> =
        withContext<NetworkMoviesResponse>(dispatcher) {
            assets.open(UP_COMING_MOVIES_ASSET).use(json::decodeFromStream)
        }.let {
            ResultState.Success(it)
        }

    override suspend fun fetchMovieDetail(movieId: Long, language: String?): ResultState<NetworkMovieDetail> =
        withContext<NetworkMovieDetail>(dispatcher) {
            assets.open(MOVIE_DETAIL_ASSET).use(json::decodeFromStream)
        }.let {
            ResultState.Success(it)
        }

    companion object {
        private const val POPULAR_MOVIES_ASSET = "popular_movies.json"
        private const val TOP_RATED_MOVIES_ASSET = "top_rated_movies.json"
        private const val UP_COMING_MOVIES_ASSET = "upcoming_movies.json"
        private const val MOVIE_DETAIL_ASSET = "movie_detail.json"
    }
}
