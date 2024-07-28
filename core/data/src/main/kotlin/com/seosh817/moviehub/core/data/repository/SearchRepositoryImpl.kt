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
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : SearchRepository {

    override fun searchMovies(query: String, language: String?): Flow<PagingData<MovieOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource { page ->
                    searchRemoteDataSource.searchMovies(query, page, language)
                }
            }
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
