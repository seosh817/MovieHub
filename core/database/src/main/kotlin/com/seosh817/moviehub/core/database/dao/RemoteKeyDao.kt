package com.seosh817.moviehub.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seosh817.moviehub.core.database.model.RemoteKeyEntity
import com.seosh817.moviehub.core.model.MovieType

@Dao
interface RemoteKeyDao {

    /**
     * Inserts a list of [RemoteKeyEntity] into the remote_keys table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>) : List<Long>

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
