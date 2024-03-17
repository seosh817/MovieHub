package com.seosh817.moviehub.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.seosh817.moviehub.core.data.model.asExternalModel
import com.seosh817.moviehub.core.data.paging.MoviesPagingSource
import com.seosh817.moviehub.core.domain.repository.SearchRepository
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMovieOverview
import com.seosh817.moviehub.core.network.source.SearchRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun searchMovies(query: String, page: Int, language: String?): Flow<PagingData<MovieOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MoviesPagingSource {
                    searchRemoteDataSource.searchMovies(query, page, language)
                }
            },
        )
            .flow
            .map { pagingData ->
                pagingData.map(NetworkMovieOverview::asExternalModel)
            }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
