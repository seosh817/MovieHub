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
import com.seosh817.moviehub.core.database.model.MovieEntity
import com.seosh817.moviehub.core.model.MovieType
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MovieDaoTest {

    private lateinit var movieDao: MovieDao
    private lateinit var db: MovieHubDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MovieHubDatabase::class.java,
        ).build()
        movieDao = db.movieDao()
    }

    @Test
    fun movieDao_get_popular_movie_by_id_test() = runTest {
        val movieEntities = listOf(
            MovieEntity(
                id = 1,
                movieId = 1L,
                adult = false,
                backdropPath = "/backdropPath",
                genreIds = listOf(1L),
                originalLanguage = "en",
                originalTitle = "originalTitle",
                overview = "overview",
                popularity = 1.0,
                posterPath = "/posterPath",
                releaseDate = "releaseDate",
                title = "title",
                video = false,
                voteAverage = 1.0,
                voteCount = null,
                type = MovieType.POPULAR,
                page = 1,
            ),
            MovieEntity(
                id = 2,
                movieId = 2L,
                adult = false,
                backdropPath = "/backdropPath",
                genreIds = listOf(2L),
                originalLanguage = "en",
                originalTitle = "originalTitle",
                overview = "overview",
                popularity = 1.0,
                posterPath = "/posterPath",
                releaseDate = "releaseDate",
                title = "title",
                video = false,
                voteAverage = 1.0,
                voteCount = null,
                type = MovieType.POPULAR,
                page = 1,
            ),
            MovieEntity(
                id = 3,
                movieId = 3L,
                adult = false,
                backdropPath = "/backdropPath",
                genreIds = listOf(1L),
                originalLanguage = "en",
                originalTitle = "originalTitle",
                overview = "overview",
                popularity = 1.0,
                posterPath = "/posterPath",
                releaseDate = "releaseDate",
                title = "title",
                video = false,
                voteAverage = 1.0,
                voteCount = null,
                type = MovieType.POPULAR,
                page = 1,
            ),
        )

        movieDao.insertAll(movieEntities)
        val movie = movieDao.getMovieById(MovieType.POPULAR, 1L)

        assertEquals(
            movieEntities[0],
            movie,
        )
    }

    @Test
    fun movieDao_room_pagingSource_test() = runTest {
        val movieEntities = listOf(
            MovieEntity(
                id = 1,
                movieId = 1L,
                adult = false,
                backdropPath = "/backdropPath",
                genreIds = listOf(1L),
                originalLanguage = "en",
                originalTitle = "originalTitle",
                overview = "overview",
                popularity = 1.0,
                posterPath = "/posterPath",
                releaseDate = "releaseDate",
                title = "title",
                video = false,
                voteAverage = 1.0,
                voteCount = null,
                type = MovieType.POPULAR,
                page = 1,
            ),
            MovieEntity(
                id = 2,
                movieId = 2L,
                adult = false,
                backdropPath = "/backdropPath",
                genreIds = listOf(2L),
                originalLanguage = "en",
                originalTitle = "originalTitle",
                overview = "overview",
                popularity = 1.0,
                posterPath = "/posterPath",
                releaseDate = "releaseDate",
                title = "title",
                video = false,
                voteAverage = 1.0,
                voteCount = null,
                type = MovieType.POPULAR,
                page = 1,
            ),
            MovieEntity(
                id = 3,
                movieId = 3L,
                adult = false,
                backdropPath = "/backdropPath",
                genreIds = listOf(1L),
                originalLanguage = "en",
                originalTitle = "originalTitle",
                overview = "overview",
                popularity = 1.0,
                posterPath = "/posterPath",
                releaseDate = "releaseDate",
                title = "title",
                video = false,
                voteAverage = 1.0,
                voteCount = null,
                type = MovieType.POPULAR,
                page = 1,
            ),
        )

        movieDao.insertAll(movieEntities)

        val pagingSource = movieDao.pagingSource(MovieType.POPULAR)

        val moviesLoadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            ),
        )

        assertEquals(movieEntities, (moviesLoadResult as? PagingSource.LoadResult.Page)?.data.orEmpty())
    }

    @Test
    fun movieDao_clear_all_movies_test() = runTest {
        val movieEntities = listOf(
            MovieEntity(
                id = 1,
                movieId = 1L,
                adult = false,
                backdropPath = "/backdropPath",
                genreIds = listOf(1L),
                originalLanguage = "en",
                originalTitle = "originalTitle",
                overview = "overview",
                popularity = 1.0,
                posterPath = "/posterPath",
                releaseDate = "releaseDate",
                title = "title",
                video = false,
                voteAverage = 1.0,
                voteCount = null,
                type = MovieType.POPULAR,
                page = 1,
            ),
            MovieEntity(
                id = 2,
                movieId = 2L,
                adult = false,
                backdropPath = "/backdropPath",
                genreIds = listOf(2L),
                originalLanguage = "en",
                originalTitle = "originalTitle",
                overview = "overview",
                popularity = 1.0,
                posterPath = "/posterPath",
                releaseDate = "releaseDate",
                title = "title",
                video = false,
                voteAverage = 1.0,
                voteCount = null,
                type = MovieType.POPULAR,
                page = 1,
            ),
            MovieEntity(
                id = 3,
                movieId = 3L,
                adult = false,
                backdropPath = "/backdropPath",
                genreIds = listOf(1L),
                originalLanguage = "en",
                originalTitle = "originalTitle",
                overview = "overview",
                popularity = 1.0,
                posterPath = "/posterPath",
                releaseDate = "releaseDate",
                title = "title",
                video = false,
                voteAverage = 1.0,
                voteCount = null,
                type = MovieType.POPULAR,
                page = 1,
            ),
        )

        movieDao.insertAll(movieEntities)

        movieDao.clearAll(MovieType.POPULAR)

        val pagingSource = movieDao.pagingSource(MovieType.UNKNOWN)

        val movies = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            ),
        )

        assertEquals(
            listOf(),
            (movies as? PagingSource.LoadResult.Page)?.data.orEmpty(),
        )
    }
}
