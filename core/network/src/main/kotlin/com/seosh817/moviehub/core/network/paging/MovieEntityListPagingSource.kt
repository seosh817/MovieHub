package com.seosh817.moviehub.core.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.seosh817.common.network.exception.NetworkException
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.network.model.movie_list.MovieEntity
import com.seosh817.moviehub.core.network.model.movie_list.MovieResponseEntity

class MovieEntityListPagingSource(
    private val loader: suspend (Int) -> ResultState<MovieResponseEntity>
) : PagingSource<Int, MovieEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val page = params.key ?: MOVIE_INITIAL_PAGE_INDEX
        
        return loader(page).map(
            {
                LoadResult.Page(
                    data = it.data.results,
                    prevKey = if (page == MOVIE_INITIAL_PAGE_INDEX) null else page - 1,
                    nextKey = if (it.data.results.isEmpty()) null else page + 1
                )
            }, {
                when (it) {
                    is ResultState.Failure.Error -> LoadResult.Error(NetworkException(it.code, it.message))
                    is ResultState.Failure.Exception -> LoadResult.Error(it.e)
                }
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val MOVIE_INITIAL_PAGE_INDEX = 1
    }
}
