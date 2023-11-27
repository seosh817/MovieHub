package com.seosh817.moviehub.core.domain.di

import com.seosh817.moviehub.core.domain.usecase.movies.GetMoviesUseCase
import com.seosh817.moviehub.core.domain.usecase.movies.GetPopularMoviesUseCase
import com.seosh817.moviehub.core.domain.usecase.movies.GetTopRatedMoviesUseCase
import com.seosh817.moviehub.core.domain.usecase.movies.GetUpcomingMoviesUseCase
import dagger.Binds
import javax.inject.Singleton

interface DomainModule {

    @Singleton
    @Binds
    fun bindGetPopularMoviesUseCase(getPopularMoviesUseCase: GetPopularMoviesUseCase): GetMoviesUseCase

    @Singleton
    @Binds
    fun bindGetTopRatedMoviesUseCase(getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase): GetMoviesUseCase

    @Singleton
    @Binds
    fun bindGetUpcomingMoviesUseCase(getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase): GetMoviesUseCase
}
