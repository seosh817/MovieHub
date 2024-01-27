package com.seosh817.moviehub.core.data.repository

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.FavoritesRepository
import com.seosh817.moviehub.core.domain.repository.UserFavoritesRepository
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CompositeFavoritesRepository @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val appPreferencesRepository: AppPreferencesRepository
) : UserFavoritesRepository {

    override fun observeFavoritesUserMovies(language: String?, scope: CoroutineScope): Flow<PagingData<UserMovie>> {
        val pagingFlow = favoritesRepository.getFavoritesPagingSource().cachedIn(scope)
        return pagingFlow
            .combine(appPreferencesRepository.userSettings) { pagingData, userSettings ->
                pagingData.map { movieOverview ->
                    UserMovie(movieOverview, userSettings)
                }
            }
    }
}