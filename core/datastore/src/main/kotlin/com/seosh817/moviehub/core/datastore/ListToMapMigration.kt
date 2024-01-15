package com.seosh817.moviehub.core.datastore

import androidx.datastore.core.DataMigration

/**
 * Migrates from using lists to maps for user data.
 */
object ListToMapMigration : DataMigration<UserPreferences> {

    override suspend fun cleanUp() = Unit

    override suspend fun migrate(currentData: UserPreferences): UserPreferences =
        currentData.copy {
            // Migrate bookmarked movie id lists
            bookmarkedMovieIds.clear()
            bookmarkedMovieIds.putAll(
                currentData.deprecatedLongBookmarkedMovieIdsList.associateWith { true },
            )
            deprecatedLongBookmarkedMovieIds.clear()

            // Mark migration as complete
            hasDoneListToMapMigration = true
        }

    override suspend fun shouldMigrate(currentData: UserPreferences): Boolean {
        return !currentData.hasDoneListToMapMigration
    }
}
