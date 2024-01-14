package com.seosh817.moviehub.core.network.di

import com.seosh817.moviehub.core.network.impl.CreditsDataSourceImpl
import com.seosh817.moviehub.core.network.impl.MovieDataSourceImpl
import com.seosh817.moviehub.core.network.source.CreditsRemoteDataSource
import com.seosh817.moviehub.core.network.source.MovieRemoteDataSource
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
    fun bindMovieDataSource(movieDataSourceImpl: MovieDataSourceImpl): MovieRemoteDataSource

    @Singleton
    @Binds
    fun bindCreditsDataSource(creditsDataSourceImpl: CreditsDataSourceImpl): CreditsRemoteDataSource
}
