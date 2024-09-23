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
package com.seosh817.moviehub.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.seosh817.common.network.exception.NetworkException
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMovieOverview
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse

class MoviesPagingSource(
    private val loader: suspend (Int) -> ResultState<NetworkMoviesResponse>,
) : PagingSource<Int, NetworkMovieOverview>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkMovieOverview> {
        val page = params.key ?: MOVIE_INITIAL_PAGE_INDEX

        return loader
            .invoke(page)
            .map(
                {
                    if (it.data.results.isNotEmpty()) {
                        LoadResult.Page(
                            data = it.data.results,
                            prevKey = page - 1,
                            nextKey = page + 1,
                        )
                    } else {
                        LoadResult.Error(IllegalStateException("Movie list is empty"))
                    }
                },
                {
                    when (it) {
                        is ResultState.Failure.Error -> LoadResult.Error(NetworkException(it.code, it.message))
                        is ResultState.Failure.Exception -> LoadResult.Error(it.e)
                    }
                },
            )
    }

    override fun getRefreshKey(state: PagingState<Int, NetworkMovieOverview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val MOVIE_INITIAL_PAGE_INDEX = 1
    }
}
