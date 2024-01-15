package com.seosh817.moviehub.core.database.dao.di

import com.seosh817.moviehub.core.database.AppDatabase
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
        database: AppDatabase,
    ): MovieDao = database.movieDao()
}
