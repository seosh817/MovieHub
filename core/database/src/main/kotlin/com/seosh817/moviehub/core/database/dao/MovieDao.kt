package com.seosh817.moviehub.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.seosh817.moviehub.core.database.model.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [MovieOverview] and [MovieEntity] access
 */
@Dao
interface MovieDao {

    @Transaction
    @Query(
        value = """
            SELECT * FROM movies
            WHERE id IN (:bookmarkedMovieIds)
            ORDER BY release_date DESC
    """,
    )
    fun getMovies(
        bookmarkedMovieIds: Set<Long> = emptySet(),
    ): Flow<List<MovieEntity>>

    /**
     * Inserts [entities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreMovieEntity(entities: List<MovieEntity>): List<Long>

    @Upsert
    suspend fun upsertNewsResources(newsResourceEntities: List<MovieEntity>)

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM movies
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteMovies(ids: List<Long>)
}