package com.seosh817.moviehub.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    val label: String,
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "prev_key")
    val prevKey: Long?,
    @ColumnInfo(name = "next_key")
    val nextKey: Long?
)
