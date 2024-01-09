package com.seosh817.moviehub.core.data.di

import com.seosh817.moviehub.core.data.repository.AppPreferencesRepositoryImpl
import com.seosh817.moviehub.core.data.repository.MovieRepositoryImpl
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.MovieRepository
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
    fun bindAppPreferencesSettingsRepository(appPreferencesRepositoryImpl: AppPreferencesRepositoryImpl): AppPreferencesRepository
}
