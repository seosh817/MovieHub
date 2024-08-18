package com.seosh817.moviehub.core.network.source

import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse

interface SearchRemoteDataSource {

    suspend fun searchMovies(query: String, page: Int, language: String? = null): ResultState<NetworkMoviesResponse>
}