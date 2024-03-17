package com.seosh817.moviehub.core.data.repository

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.SearchRepository
import com.seosh817.moviehub.core.domain.repository.UserSearchRepository
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CompositeSearchRepository @Inject constructor(
    private val searchRepository: SearchRepository,
    private val appPreferencesRepository: AppPreferencesRepository
) : UserSearchRepository {

    override suspend fun searchMovies(query: String, page: Int, scope: CoroutineScope, language: String?): Flow<PagingData<UserMovie>> {
        val pagingFlow = searchRepository.searchMovies(query, page, language).cachedIn(scope)
        return pagingFlow
            .combine(appPreferencesRepository.userSettings) { pagingData, userSettings ->
                pagingData.map { movieOverview ->
                    UserMovie(movieOverview, userSettings)
                }
            }
    }
}
