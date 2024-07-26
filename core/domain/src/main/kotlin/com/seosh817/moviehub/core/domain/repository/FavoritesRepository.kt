package com.seosh817.moviehub.core.domain.repository

import androidx.paging.PagingData
import com.seosh817.moviehub.core.model.MovieOverview
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavoritesPagingSource(): Flow<PagingData<MovieOverview>>

    suspend fun insert(movie: MovieOverview): Long?

    suspend fun delete(id: Long)
}
