package com.seosh817.moviehub.core.domain.usecase.movies

import com.seosh817.common.result.extension.fetchDataToFlow
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCreditsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(movieId: Long, language: String? = null) = fetchDataToFlow {
        movieRepository.fetchMovieCredits(movieId, language)
    }
        .map {
            it.copy(
                cast = it.cast?.distinctBy { cast -> cast.id },
                crew = it.crew?.distinctBy { crew -> crew.id }
            )
        }
        .flowOn(dispatcher)
}