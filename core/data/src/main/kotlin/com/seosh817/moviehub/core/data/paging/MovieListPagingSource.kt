package com.seosh817.moviehub.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.seosh817.common.network.exception.NetworkException
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.model.MoviesResponse

class MovieListPagingSource constructor(
    private val loader: suspend (Int) -> ResultState<MoviesResponse>
) : PagingSource<Int, MovieOverview>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieOverview> {
        val page = params.key ?: MOVIE_INITIAL_PAGE_INDEX

        return loader
            .invoke(page)
            .map(
                {
                    if (it.data.results.isNotEmpty()) {
                        LoadResult.Page(
                            data = it.data.results,
                            prevKey = page - 1,
                            nextKey = page + 1
                        )
                    } else {
                        LoadResult.Error(IllegalStateException("Movie list is empty"))
                    }
                }, {
                    when (it) {
                        is ResultState.Failure.Error -> LoadResult.Error(NetworkException(it.code, it.message))
                        is ResultState.Failure.Exception -> LoadResult.Error(it.e)
                    }
                }
            )
    }

    override fun getRefreshKey(state: PagingState<Int, MovieOverview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val MOVIE_INITIAL_PAGE_INDEX = 1
    }
}