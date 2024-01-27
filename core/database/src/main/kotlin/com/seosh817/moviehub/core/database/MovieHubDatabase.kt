package com.seosh817.moviehub.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seosh817.moviehub.core.database.converter.LongListConverter
import com.seosh817.moviehub.core.database.converter.MovieTypeConverter
import com.seosh817.moviehub.core.database.dao.FavoritesDao
import com.seosh817.moviehub.core.database.dao.MovieDao
import com.seosh817.moviehub.core.database.dao.RemoteKeyDao
import com.seosh817.moviehub.core.database.model.FavoriteEntity
import com.seosh817.moviehub.core.database.model.MovieEntity
import com.seosh817.moviehub.core.database.model.RemoteKeyEntity

@Database(
    entities = [
        MovieEntity::class,
        RemoteKeyEntity::class,
        FavoriteEntity::class
    ],
    version = 2,
    exportSchema = false,
)
@TypeConverters(
    LongListConverter::class,
    MovieTypeConverter::class
)
abstract class MovieHubDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun remoteKeyDao(): RemoteKeyDao

    abstract fun favoritesDao(): FavoritesDao
}