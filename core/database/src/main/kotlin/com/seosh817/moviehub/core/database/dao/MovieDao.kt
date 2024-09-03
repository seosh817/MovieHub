package com.seosh817.moviehub.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seosh817.moviehub.core.database.model.MovieEntity
import com.seosh817.moviehub.core.model.MovieType

/**
 * DAO for [MovieEntity] access
 */
@Dao
interface MovieDao {

    /**
     * Returns a [PagingSource] of [MovieEntity] of a given [type]
     */
    @Query(
        value = """
            SELECT * FROM movies
            WHERE type = :type
            ORDER BY page ASC
    """
    )
    fun pagingSource(type: MovieType): PagingSource<Int, MovieEntity>

    /**
     * Returns list of [MovieEntity] of a given [id]
     */
    @Query(
        value = """
            SELECT * from movies
            WHERE type = :type
            AND movie_id = :id
            """
    )
    suspend fun getMovieById(
        type: MovieType,
        id: Long,
    ): MovieEntity?

    /**
     * Inserts a list of [MovieEntity] into the movies table
     * @return a list of row ids
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<MovieEntity>): List<Long>

    /**
     * Deletes all movies
     */
    @Query("DELETE FROM movies")
    suspend fun clearAll()
}
