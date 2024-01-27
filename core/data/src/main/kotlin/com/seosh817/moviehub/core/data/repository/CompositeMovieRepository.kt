package com.seosh817.moviehub.core.data.repository

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.domain.repository.UserMovieRepository
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CompositeMovieRepository @Inject constructor(
    private val movieRepository: MovieRepository,
    private val appPreferencesRepository: AppPreferencesRepository,
) : UserMovieRepository {

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