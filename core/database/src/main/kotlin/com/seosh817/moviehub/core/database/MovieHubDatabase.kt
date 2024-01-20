package com.seosh817.moviehub.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seosh817.moviehub.core.database.converter.LongListConverter
import com.seosh817.moviehub.core.database.dao.MovieDao
import com.seosh817.moviehub.core.database.dao.RemoteKeyDao
import com.seosh817.moviehub.core.database.model.MovieEntity
import com.seosh817.moviehub.core.database.model.RemoteKey

@Database(
    entities = [
        MovieEntity::class,
        RemoteKey::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    LongListConverter::class,
)
abstract class MovieHubDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}