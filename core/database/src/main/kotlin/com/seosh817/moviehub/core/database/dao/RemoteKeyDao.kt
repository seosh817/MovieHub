package com.seosh817.moviehub.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seosh817.moviehub.core.database.model.RemoteKey
import com.seosh817.moviehub.core.model.MovieType

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(remoteKey: List<RemoteKey>)

    @Query("Select * From remote_keys Where movie_id = :id")
    suspend fun getRemoteKeyByMovieID(id: Long): RemoteKey?

    @Query("DELETE FROM remote_keys WHERE type = :type")
    suspend fun clearAll(type: MovieType)

    @Query("Select created_at From remote_keys Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
