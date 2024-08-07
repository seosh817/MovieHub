package com.seosh817.moviehub.core.domain.usecase

import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.FavoritesRepository
import com.seosh817.moviehub.core.domain.repository.MoviesRepository
import com.seosh817.moviehub.core.model.MovieType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostBookmarkUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository,
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(movieType: MovieType, id: Long, bookmarked: Boolean) = flow {
        delay(1000L) // For testing
        val movie = moviesRepository.getMovieById(movieType, id)
        checkNotNull(movie) { "Movie not found" }

        if (bookmarked) {
            favoritesRepository.insert(movie)
        } else {
            favoritesRepository.delete(movie.id)
        }
        appPreferencesRepository.setBookMarkedMovieIds(movie.id, bookmarked)
        emit(bookmarked)
    }
        .flowOn(dispatcher)
}
