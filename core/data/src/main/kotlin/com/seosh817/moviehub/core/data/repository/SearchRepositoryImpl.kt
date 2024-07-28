package com.seosh817.moviehub.core.data.repository

import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.data.model.asExternalModel
import com.seosh817.moviehub.core.domain.repository.SearchRepository
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.network.source.SearchRemoteDataSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun searchMovies(query: String, page: Int, language: String?): ResultState<List<MovieOverview>> {
        return searchRemoteDataSource.searchMovies(query, page, language)
            .map {
                it.results.map { movie -> movie.asExternalModel() }
            }
    }
}
