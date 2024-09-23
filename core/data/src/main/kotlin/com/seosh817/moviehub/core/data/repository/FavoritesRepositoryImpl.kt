/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seosh817.moviehub.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.seosh817.moviehub.core.database.dao.FavoritesDao
import com.seosh817.moviehub.core.database.model.asExternalModel
import com.seosh817.moviehub.core.database.model.asFavoriteEntity
import com.seosh817.moviehub.core.domain.repository.FavoritesRepository
import com.seosh817.moviehub.core.model.MovieOverview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao,
) : FavoritesRepository {

    override fun getFavoritesPagingSource(): Flow<PagingData<MovieOverview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { favoritesDao.pagingSource() },
        ).flow
            .map { pagingData ->
                pagingData.map { favoriteEntity ->
                    favoriteEntity.asExternalModel()
                }
            }
    }

    override suspend fun insert(movie: MovieOverview): Long? {
        return favoritesDao.insert(movie.asFavoriteEntity())
    }

    override suspend fun delete(id: Long) {
        return favoritesDao.delete(id)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
