package com.seosh817.moviehub.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seosh817.moviehub.core.database.model.FavoriteEntity

@Dao
interface FavoritesDao {

    @Query(
        value = "SELECT * FROM favorites ORDER BY created_at ASC"
    )
    fun pagingSource(): PagingSource<Int, FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteEntity): Long?

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun delete(id: Long)
}
