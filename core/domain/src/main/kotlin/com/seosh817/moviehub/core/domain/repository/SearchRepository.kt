package com.seosh817.moviehub.core.domain.repository

import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.model.MovieOverview

interface SearchRepository {

    suspend fun searchMovies(query: String, page: Int, language: String?): ResultState<List<MovieOverview>>
}
