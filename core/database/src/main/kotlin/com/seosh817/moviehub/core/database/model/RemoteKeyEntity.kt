package com.seosh817.moviehub.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.RemoteKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val type: MovieType = MovieType.UNKNOWN,
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "prev_key")
    val prevKey: Long?,
    @ColumnInfo(name = "next_key")
    val nextKey: Long?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
