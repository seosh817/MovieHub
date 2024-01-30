package com.seosh817.moviehub.core.domain.repository

import androidx.paging.PagingData
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface UserMoviesRepository {

    fun observePopularUserMovies(language: String?, scope: CoroutineScope): Flow<PagingData<UserMovie>>
}
