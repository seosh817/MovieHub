package com.seosh817.moviehub.core.domain.usecase.movies

import androidx.paging.PagingData
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.model.MovieOverview
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : GetMoviesUseCase {

    override operator fun invoke(language: String?): Flow<PagingData<MovieOverview>> = movieRepository
        .fetchPopularMovies(language)
        .catch {
            ResultState.Failure.Exception(it)
        }
        .flowOn(dispatcher)
}
