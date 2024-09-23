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
package com.seosh817.moviehub.core.domain.di

import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.CreditsRepository
import com.seosh817.moviehub.core.domain.repository.FavoritesRepository
import com.seosh817.moviehub.core.domain.repository.MoviesRepository
import com.seosh817.moviehub.core.domain.repository.UserSearchRepository
import com.seosh817.moviehub.core.domain.usecase.GetCreditsUseCase
import com.seosh817.moviehub.core.domain.usecase.GetMovieDetailUseCase
import com.seosh817.moviehub.core.domain.usecase.GetSearchMoviesUseCase
import com.seosh817.moviehub.core.domain.usecase.PostBookmarkUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideMovieDetailUseCase(
        moviesRepository: MoviesRepository,
        @Dispatcher(MovieHubDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(moviesRepository, dispatcher)
    }

    @Singleton
    @Provides
    fun provideGetCreditsUseCase(
        creditsRepository: CreditsRepository,
        @Dispatcher(MovieHubDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): GetCreditsUseCase {
        return GetCreditsUseCase(creditsRepository, dispatcher)
    }

    @Singleton
    @Provides
    fun providePostBookmarkUseCase(
        appPreferencesRepository: AppPreferencesRepository,
        favoritesRepository: FavoritesRepository,
        @Dispatcher(MovieHubDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): PostBookmarkUseCase {
        return PostBookmarkUseCase(
            appPreferencesRepository,
            favoritesRepository,
            dispatcher,
        )
    }

    @Singleton
    @Provides
    fun provideGetSearchMoviesUseCase(
        userSearchRepository: UserSearchRepository,
        @Dispatcher(MovieHubDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): GetSearchMoviesUseCase {
        return GetSearchMoviesUseCase(userSearchRepository, dispatcher)
    }
}
