package com.seosh817.moviehub.core.network.fake

import JvmUnitTestFakeAssetManager
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.network.model.credits.NetworkCredits
import com.seosh817.moviehub.core.network.source.CreditsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
class FakeCreditsDataSource @Inject constructor(
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val json: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : CreditsRemoteDataSource {

    override suspend fun fetchCredits(movieId: Long, language: String?): ResultState<NetworkCredits> {
        withContext<NetworkCredits>(dispatcher) {
            assets.open(CREDITS_ASSET).use(json::decodeFromStream)
        }.let {
            return ResultState.Success(it)
        }
    }

    companion object {
        private const val CREDITS_ASSET = "credits.json"
    }
}
