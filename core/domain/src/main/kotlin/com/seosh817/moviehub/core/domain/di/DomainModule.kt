package com.seosh817.moviehub.core.domain.di

import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.CreditsRepository
import com.seosh817.moviehub.core.domain.repository.FavoritesRepository
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.domain.repository.UserMovieRepository
import com.seosh817.moviehub.core.domain.usecase.GetCreditsUseCase
import com.seosh817.moviehub.core.domain.usecase.GetMovieDetailUseCase
import com.seosh817.moviehub.core.domain.usecase.GetPopularMoviesUseCase
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
    fun provideGetPopularMoviesUseCase(
        movieRepository: UserMovieRepository,
        @Dispatcher(MovieHubDispatchers.IO) dispatcher: CoroutineDispatcher
    ): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(movieRepository, dispatcher)
    }

    @Singleton
    @Provides
    fun provideMovieDetailUseCase(
        movieRepository: MovieRepository,
        @Dispatcher(MovieHubDispatchers.IO) dispatcher: CoroutineDispatcher
    ): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(movieRepository, dispatcher)
    }

    @Singleton
    @Provides
    fun provideGetCreditsUseCase(
        creditsRepository: CreditsRepository,
        @Dispatcher(MovieHubDispatchers.IO) dispatcher: CoroutineDispatcher
    ): GetCreditsUseCase {
        return GetCreditsUseCase(creditsRepository, dispatcher)
    }

    @Singleton
    @Provides
    fun providePostBookmarkUseCase(
        appPreferencesRepository: AppPreferencesRepository,
        movieRepository: MovieRepository,
        favoritesRepository: FavoritesRepository,
        @Dispatcher(MovieHubDispatchers.IO) dispatcher: CoroutineDispatcher
    ): PostBookmarkUseCase {
        return PostBookmarkUseCase(
            appPreferencesRepository,
            movieRepository,
            favoritesRepository,
            dispatcher
        )
    }
}
