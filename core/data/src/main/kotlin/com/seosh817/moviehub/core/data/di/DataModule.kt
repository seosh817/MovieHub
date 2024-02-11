package com.seosh817.moviehub.core.data.di

import com.seosh817.moviehub.core.data.repository.AppPreferencesRepositoryImpl
import com.seosh817.moviehub.core.data.repository.AppVersionsRepositoryImpl
import com.seosh817.moviehub.core.data.repository.CompositeFavoritesRepository
import com.seosh817.moviehub.core.data.repository.CompositePopularMoviesRepository
import com.seosh817.moviehub.core.data.repository.CreditsRepositoryImpl
import com.seosh817.moviehub.core.data.repository.FavoritesRepositoryImpl
import com.seosh817.moviehub.core.data.repository.MoviesRepositoryImpl
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.AppVersionsRepository
import com.seosh817.moviehub.core.domain.repository.CreditsRepository
import com.seosh817.moviehub.core.domain.repository.FavoritesRepository
import com.seosh817.moviehub.core.domain.repository.MoviesRepository
import com.seosh817.moviehub.core.domain.repository.UserFavoritesRepository
import com.seosh817.moviehub.core.domain.repository.UserMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Singleton
    @Binds
    fun bindCreditsRepository(creditsRepositoryImpl: CreditsRepositoryImpl): CreditsRepository

    @Singleton
    @Binds
    fun bindAppPreferencesSettingsRepository(appPreferencesRepositoryImpl: AppPreferencesRepositoryImpl): AppPreferencesRepository

    @Singleton
    @Binds
    fun bindFavoritesRepository(favoritesRepository: FavoritesRepositoryImpl): FavoritesRepository

    @Singleton
    @Binds
    fun bindCompositeFavoritesRepository(compositeFavoritesRepository: CompositeFavoritesRepository): UserFavoritesRepository

    @Singleton
    @Binds
    fun bindCompositePopularMoviesRepository(compositePopularMoviesRepository: CompositePopularMoviesRepository): UserMoviesRepository

    @Singleton
    @Binds
    fun bindAppVersionsRepository(appVersionsRepository: AppVersionsRepositoryImpl): AppVersionsRepository
}
