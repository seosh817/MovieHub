package com.seosh817.moviehub.core.network.impl

import com.seosh817.common.network.handleApi
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse
import com.seosh817.moviehub.core.network.service.search.SearchService
import com.seosh817.moviehub.core.network.source.SearchRemoteDataSource
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchService: SearchService
) : SearchRemoteDataSource {

    override suspend fun searchMovies(query: String, page: Int, language: String?): ResultState<NetworkMoviesResponse> {
        return handleApi {
            searchService.searchMovies(query, page, language)
        }
    }
}
