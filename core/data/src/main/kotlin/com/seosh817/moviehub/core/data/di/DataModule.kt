package com.seosh817.moviehub.core.data.di

import com.seosh817.moviehub.core.data.repository.AppPreferencesRepositoryImpl
import com.seosh817.moviehub.core.data.repository.CompositeFavoritesRepository
import com.seosh817.moviehub.core.data.repository.CompositeMovieRepository
import com.seosh817.moviehub.core.data.repository.CreditsRepositoryImpl
import com.seosh817.moviehub.core.data.repository.FavoritesRepositoryImpl
import com.seosh817.moviehub.core.data.repository.MovieRepositoryImpl
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.CreditsRepository
import com.seosh817.moviehub.core.domain.repository.FavoritesRepository
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.domain.repository.UserFavoritesRepository
import com.seosh817.moviehub.core.domain.repository.UserMovieRepository
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
    fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Singleton
    @Binds
    fun bindCreditsRepository(creditsRepositoryImpl: CreditsRepositoryImpl): CreditsRepository

    @Singleton
    @Binds
    fun bindAppPreferencesSettingsRepository(appPreferencesRepositoryImpl: AppPreferencesRepositoryImpl): AppPreferencesRepository

    @Singleton
    @Binds
    fun bindCompositeMovieRepository(compositeMovieRepositoryImpl: CompositeMovieRepository): UserMovieRepository

    @Singleton
    @Binds
    fun bindFavoritesRepository(favoritesRepository: FavoritesRepositoryImpl): FavoritesRepository

    @Singleton
    @Binds
    fun bindCompositeFavoritesRepository(compositeFavoritesRepository: CompositeFavoritesRepository): UserFavoritesRepository
}
