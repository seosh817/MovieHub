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
package com.seosh817.moviehub.core.network.impl

import com.seosh817.common.network.handleApi
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse
import com.seosh817.moviehub.core.network.service.search.SearchService
import com.seosh817.moviehub.core.network.source.SearchRemoteDataSource
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchService: SearchService,
) : SearchRemoteDataSource {

    override suspend fun searchMovies(query: String, page: Int, language: String?): ResultState<NetworkMoviesResponse> {
        return handleApi {
            searchService.searchMovies(query, page, language)
        }
    }
}
