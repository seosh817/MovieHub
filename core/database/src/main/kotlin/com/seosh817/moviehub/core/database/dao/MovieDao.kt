package com.seosh817.moviehub.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seosh817.moviehub.core.database.model.MovieEntity

/**
 * DAO for [MovieEntity] access
 */
@Dao
interface MovieDao {

    @Query(
        value = """
            SELECT * FROM movies
            ORDER BY page ASC
    """)
    fun pagingSource(): PagingSource<Int, MovieEntity>

    /**
     * Inserts [entities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<MovieEntity>): List<Long>

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}