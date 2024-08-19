package com.seosh817.moviehub.core.domain.repository

import androidx.paging.PagingData
import com.seosh817.moviehub.core.model.MovieOverview
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchMovies(query: String, language: String?): Flow<PagingData<MovieOverview>>
}
