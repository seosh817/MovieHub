package com.seosh817.moviehub.core.data.repository

import com.seosh817.common.result.extension.fetchDataToFlow
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

    override fun searchMovies(query: String, page: Int, language: String?, scope: CoroutineScope): Flow<List<UserMovie>> {
        return fetchDataToFlow { searchRepository.searchMovies(query, page, language) }
            .combine(appPreferencesRepository.userSettings) { movieOverviews, userSettings ->
                movieOverviews.map { movieOverview ->
                    UserMovie(movieOverview, userSettings)
                }
            }
    }
}
