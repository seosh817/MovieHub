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
package com.seosh817.moviehub.core.database.dao

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.seosh817.moviehub.core.database.MovieHubDatabase
import com.seosh817.moviehub.core.database.model.FavoriteEntity
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FavoritesDaoTest {

    private lateinit var favoritesDao: FavoritesDao
    private lateinit var db: MovieHubDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MovieHubDatabase::class.java,
        ).build()
        favoritesDao = db.favoritesDao()
    }

    @Test
    fun favoritesDao_room_pagingSource_insert_favorite_test() = runTest {
        val favorite1 = FavoriteEntity(
            id = 1,
            title = "title",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            overview = "overview",
            releaseDate = "releaseDate",
            voteAverage = 1.0,
            voteCount = 1,
            adult = false,
            genreIds = listOf(1L),
            originalLanguage = "en",
            originalTitle = "originalTitle",
            popularity = 1.0,
            video = false,
            createdAt = 0L,
        )

        val favorite2 = FavoriteEntity(
            id = 2,
            title = "title",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            overview = "overview",
            releaseDate = "releaseDate",
            voteAverage = 1.0,
            voteCount = 1,
            adult = false,
            genreIds = listOf(1L),
            originalLanguage = "en",
            originalTitle = "originalTitle",
            popularity = 1.0,
            video = false,
            createdAt = 0L,
        )

        val favoriteEntities = listOf(favorite1, favorite2)

        favoritesDao.insert(favorite1)
        favoritesDao.insert(favorite2)

        val pagingSource = favoritesDao.pagingSource()

        val favoritesLoadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false,
            ),
        )

        assertEquals(favoriteEntities, (favoritesLoadResult as? PagingSource.LoadResult.Page)?.data.orEmpty())
    }

    @Test
    fun favoritesDao_delete_favorite_test() = runTest {
        val favorite1 = FavoriteEntity(
            id = 1,
            title = "title",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            overview = "overview",
            releaseDate = "releaseDate",
            voteAverage = 1.0,
            voteCount = 1,
            adult = false,
            genreIds = listOf(1L),
            originalLanguage = "en",
            originalTitle = "originalTitle",
            popularity = 1.0,
            video = false,
            createdAt = 0L,
        )

        val favorite2 = FavoriteEntity(
            id = 2,
            title = "title",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            overview = "overview",
            releaseDate = "releaseDate",
            voteAverage = 1.0,
            voteCount = 1,
            adult = false,
            genreIds = listOf(1L),
            originalLanguage = "en",
            originalTitle = "originalTitle",
            popularity = 1.0,
            video = false,
            createdAt = 0L,
        )

        val favoriteEntities = listOf(favorite1, favorite2)

        favoritesDao.insert(favorite1)
        favoritesDao.insert(favorite2)

        favoritesDao.delete(favorite1.id)

        val pagingSource = favoritesDao.pagingSource()

        val favoritesLoadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false,
            ),
        )

        assertEquals(listOf(favorite2), (favoritesLoadResult as? PagingSource.LoadResult.Page)?.data.orEmpty())
    }
}
