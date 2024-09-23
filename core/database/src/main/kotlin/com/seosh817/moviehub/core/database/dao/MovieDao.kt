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
    """,
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
            """,
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
