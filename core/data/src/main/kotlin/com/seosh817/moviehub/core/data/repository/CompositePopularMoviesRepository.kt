package com.seosh817.moviehub.core.data.repository

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.MoviesRepository
import com.seosh817.moviehub.core.domain.repository.UserFavoritesRepository
import com.seosh817.moviehub.core.domain.repository.UserMoviesRepository
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CompositePopularMoviesRepository @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val appPreferencesRepository: AppPreferencesRepository
) : UserMoviesRepository {

    override fun observePopularUserMovies(language: String?, scope: CoroutineScope): Flow<PagingData<UserMovie>> {
        val pagingFlow = moviesRepository.fetchPopularMovies(language).cachedIn(scope)
        return pagingFlow
            .combine(appPreferencesRepository.userSettings) { pagingData, userSettings ->
                pagingData.map { movieOverview ->
                    UserMovie(movieOverview, userSettings)
                }
            }
    }
}
