package com.seosh817.moviehub.core.database.dao

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.seosh817.moviehub.core.database.MovieHubDatabase
import com.seosh817.moviehub.core.database.model.FavoriteEntity
import com.seosh817.moviehub.core.database.model.MovieEntity
import com.seosh817.moviehub.core.model.MovieType
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
            createdAt = 0L
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
            createdAt = 0L
        )

        val favoriteEntities = listOf(favorite1, favorite2)

        favoritesDao.insert(favorite1)
        favoritesDao.insert(favorite2)

        val pagingSource = favoritesDao.pagingSource()

        val favoritesLoadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
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
            createdAt = 0L
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
            createdAt = 0L
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
                placeholdersEnabled = false
            )
        )

        assertEquals(listOf(favorite2), (favoritesLoadResult as? PagingSource.LoadResult.Page)?.data.orEmpty())
    }
}