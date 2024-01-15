package com.seosh817.moviehub.core.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.seosh817.common.network.exception.NetworkException
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.database.AppDatabase
import com.seosh817.moviehub.core.model.MovieListType
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.model.MoviesResponse

class MovieListPagingSource(
    private val loader: suspend (Int) -> ResultState<MoviesResponse>,
    private val database: AppDatabase,
    private val type: MovieListType
) : PagingSource<Int, MovieOverview>() {

    private val movieDao = database.movieDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieOverview> {
        val page = params.key ?: MOVIE_INITIAL_PAGE_INDEX

        movieDao.pagingSource()


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
        Log.d("!!!", "pagingsource state.anchorPosition: ${state.anchorPosition}, pages: ${state.pages.toList()}")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val MOVIE_INITIAL_PAGE_INDEX = 1
    }
}
