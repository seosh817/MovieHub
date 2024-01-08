package com.seosh817.moviehub.core.database.dao.di

import com.seosh817.moviehub.core.database.MovieHubDatabase
import com.seosh817.moviehub.core.database.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun providesMovieDao(
        database: MovieHubDatabase,
    ): MovieDao = database.movieDao()
}
