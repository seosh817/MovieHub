package com.seosh817.moviehub.core.network.di

import com.seosh817.moviehub.core.network.impl.CreditsDataSourceImpl
import com.seosh817.moviehub.core.network.impl.MoviesDataSourceImpl
import com.seosh817.moviehub.core.network.impl.SearchDataSourceImpl
import com.seosh817.moviehub.core.network.source.CreditsRemoteDataSource
import com.seosh817.moviehub.core.network.source.MoviesRemoteDataSource
import com.seosh817.moviehub.core.network.source.SearchRemoteDataSource
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
    fun bindMoviesDataSource(moviesDataSourceImpl: MoviesDataSourceImpl): MoviesRemoteDataSource

    @Singleton
    @Binds
    fun bindCreditsDataSource(creditsDataSourceImpl: CreditsDataSourceImpl): CreditsRemoteDataSource

    @Singleton
    @Binds
    fun bindSearchDataSource(searchDataSourceImpl: SearchDataSourceImpl): SearchRemoteDataSource
}
