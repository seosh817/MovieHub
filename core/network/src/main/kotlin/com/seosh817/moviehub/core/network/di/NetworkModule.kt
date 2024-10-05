/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seosh817.moviehub.core.network.di

import com.seosh817.moviehub.core.network.impl.CreditsDataSourceImpl
import com.seosh817.moviehub.core.network.impl.MoviesDataSourceImpl
import com.seosh817.moviehub.core.network.impl.SearchDataSourceImpl
import com.seosh817.moviehub.core.network.impl.VideoDataSourceImpl
import com.seosh817.moviehub.core.network.source.CreditsRemoteDataSource
import com.seosh817.moviehub.core.network.source.MoviesRemoteDataSource
import com.seosh817.moviehub.core.network.source.SearchRemoteDataSource
import com.seosh817.moviehub.core.network.source.VideosRemoteDataSource
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

    @Singleton
    @Binds
    fun bindVideoDataSource(videoDataSourceImpl: VideoDataSourceImpl): VideosRemoteDataSource
}
