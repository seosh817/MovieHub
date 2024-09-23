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
package com.seosh817.moviehub.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.data.model.asExternalModel
import com.seosh817.moviehub.core.data.paging.MoviesPagingSource
import com.seosh817.moviehub.core.domain.repository.SearchRepository
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.network.source.SearchRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) : SearchRepository {

    override fun searchMovies(query: String, language: String?): Flow<PagingData<MovieOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviesPagingSource { page ->
                    searchRemoteDataSource.searchMovies(query, page, language)
                }
            },
        )
            .flow
            .map { pagingData ->
                pagingData.map {
                    it.asExternalModel()
                }
            }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
