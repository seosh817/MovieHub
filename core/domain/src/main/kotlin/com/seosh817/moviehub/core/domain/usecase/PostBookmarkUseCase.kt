/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seosh817.moviehub.core.domain.usecase

import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.FavoritesRepository
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
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher,
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
