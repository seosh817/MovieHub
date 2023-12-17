package com.seosh817.moviehub.core.domain.di

import com.seosh817.moviehub.core.domain.usecase.movies.GetMoviesUseCase
import com.seosh817.moviehub.core.domain.usecase.movies.GetPopularMoviesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Singleton
    @Binds
    fun bindGetPopularMoviesUseCase(getPopularMoviesUseCase: GetPopularMoviesUseCase): GetMoviesUseCase
}
