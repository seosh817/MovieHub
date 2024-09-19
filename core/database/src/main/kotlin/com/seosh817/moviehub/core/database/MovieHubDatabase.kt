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
        FavoriteEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
@TypeConverters(
    LongListConverter::class,
    MovieTypeConverter::class,
)
abstract class MovieHubDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun remoteKeyDao(): RemoteKeyDao

    abstract fun favoritesDao(): FavoritesDao
}
