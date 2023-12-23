package com.seosh817.moviehub.core.datastore.di

import com.seosh817.moviehub.core.data.source.AppStartUpPreferencesDataSource
import com.seosh817.moviehub.core.datastore.AppStartUpPreferencesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindAppStartUpPreferencesDataSource(appStartUpPreferencesDataSourceImpl: AppStartUpPreferencesDataSourceImpl): AppStartUpPreferencesDataSource
}