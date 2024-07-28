package com.seosh817.moviehub.core.domain.repository

import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface UserSearchRepository {

    fun searchMovies(query: String, page: Int, language: String?, scope: CoroutineScope): Flow<List<UserMovie>>
}
