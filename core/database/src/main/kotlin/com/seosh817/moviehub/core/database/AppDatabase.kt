package com.seosh817.moviehub.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
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
    version = 2,
    exportSchema = false
)
@TypeConverters(
    LongListConverter::class,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}
