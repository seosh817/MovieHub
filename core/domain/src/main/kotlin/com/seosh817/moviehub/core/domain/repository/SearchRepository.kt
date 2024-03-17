package com.seosh817.moviehub.core.domain.repository

import androidx.paging.PagingData
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchMovies(query: String, page: Int, language: String?): Flow<PagingData<MovieOverview>>
}
