package com.seosh817.moviehub.core.domain.usecase

import android.util.Log
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.FavoritesRepository
import com.seosh817.moviehub.core.domain.repository.MoviesRepository
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostBookmarkUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val favoritesRepository: FavoritesRepository,
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(movieType: MovieType, userMovie: UserMovie) = flow {
        delay(1000L) // For testing
        if (!userMovie.isBookmarked) {
            favoritesRepository.insert(userMovie.toMovieOverView())
        } else {
            favoritesRepository.delete(userMovie.id)
        }
        appPreferencesRepository.setBookMarkedMovieIds(userMovie.id, !userMovie.isBookmarked)
        emit(userMovie.isBookmarked)
    }
        .flowOn(dispatcher)
}
