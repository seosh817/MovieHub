package com.seosh817.moviehub.core.database

import android.content.Context
import androidx.room.Room
import com.seosh817.moviehub.core.database.migration.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesMovieHubDatabase(
        @ApplicationContext context: Context,
    ): MovieHubDatabase = Room.databaseBuilder(
        context,
        MovieHubDatabase::class.java,
        "moviehub-database",
    )
        .addMigrations(MIGRATION_1_2)
        .build()
}
