package com.seosh817.moviehub.core.domain.usecase

import androidx.paging.PagingData
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.UserMoviesRepository
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val userMoviesRepository: UserMoviesRepository,
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : GetMoviesUseCase {

    override operator fun invoke(language: String?, scope: CoroutineScope): Flow<PagingData<UserMovie>> = userMoviesRepository
        .observePopularUserMovies(language, scope)
        .catch {
            ResultState.Failure.Exception(it)
        }
        .flowOn(dispatcher)
}
