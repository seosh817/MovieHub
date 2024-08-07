package com.seosh817.moviehub.core.domain.usecase

import com.seosh817.common.result.extension.fetchDataToFlow
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.MoviesRepository
import com.seosh817.moviehub.core.model.MovieDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) {

    operator fun invoke(movieId: Long, language: String? = null): Flow<MovieDetail> = fetchDataToFlow {
        moviesRepository.fetchMovieDetail(movieId, language)
    }
            .map {
                it.copy(
                        productionCompanies = it
                                .productionCompanies
                                ?.distinctBy { it.id }
                )
            }
        .flowOn(dispatcher)
}