package com.seosh817.moviehub.core.data.repository

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.MoviesRepository
import com.seosh817.moviehub.core.domain.repository.UserMoviesRepository
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CompositeMoviesRepository @Inject constructor(
    private val movieRepository: MoviesRepository,
    private val appPreferencesRepository: AppPreferencesRepository,
) : UserMoviesRepository {

    override fun observePopularUserMovies(language: String?, scope: CoroutineScope): Flow<PagingData<UserMovie>> {
        val pagingFlow = movieRepository.fetchPopularMovies(language).cachedIn(scope)
        return pagingFlow
            .combine(appPreferencesRepository.userSettings) { pagingData, userSettings ->
                pagingData.map { movieOverview ->
                    UserMovie(movieOverview, userSettings)
                }
            }
    }
}