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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seosh817.moviehub.core.database.model.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    /**
     * Inserts a list of [RemoteKeyEntity] into the remote_keys table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>): List<Long>

    /**
     * Returns a [RemoteKeyEntity] of a given [type]
     */
    @Query("Select * From remote_keys Where movie_id = :id")
    suspend fun getRemoteKeyByMovieID(id: Long): RemoteKeyEntity?

    /**
     * Deletes all remote keys
     */
    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()

    /**
     * Returns the creation time of the last inserted remote key
     */
    @Query("Select created_at From remote_keys Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
