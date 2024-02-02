package com.seosh817.moviehub.core.database.dao

import android.content.Context
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
    fun movieDao_get_popular_movie_by_id() = runTest {
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
                page = 1
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
                page = 1
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
                page = 1
            )
        )

        movieDao.insertAll(movieEntities)
        val movie = movieDao.getMovieById(MovieType.POPULAR, 1L)

        assertEquals(
            movie,
            movieEntities[0]
        )
    }
}