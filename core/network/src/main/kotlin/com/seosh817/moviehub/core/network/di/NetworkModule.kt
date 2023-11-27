package com.seosh817.moviehub.core.network.di

import com.seosh817.moviehub.core.data.source.MovieDataSource
import com.seosh817.moviehub.core.network.source.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Singleton
    @Binds
    fun bindMovieDataSource(movieDataSourceImpl: MovieDataSourceImpl): MovieDataSource
}
