package com.seosh817.moviehub.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seosh817.moviehub.core.database.dao.MovieDao
import com.seosh817.moviehub.core.database.model.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieHubDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}