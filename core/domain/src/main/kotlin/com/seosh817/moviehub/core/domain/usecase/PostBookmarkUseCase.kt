package com.seosh817.moviehub.core.domain.usecase

import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.FavoritesRepository
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.model.MovieType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostBookmarkUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val movieRepository: MovieRepository,
    private val favoritesRepository: FavoritesRepository,
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(movieType: MovieType, id: Long, bookmarked: Boolean) = flow<Unit> {
        val movie = movieRepository.getMovieById(movieType, id)
        checkNotNull(movie) { "Movie not found" }

        if (bookmarked) {
            favoritesRepository.insert(movie)
        } else {
            favoritesRepository.delete(movie.id)
        }
        appPreferencesRepository.setBookMarkedMovieIds(movie.id, bookmarked)
    }
        .flowOn(dispatcher)
}
