package com.seosh817.moviehub.core.datastore

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class ListToMapMigrationTest {

    @Test
    fun ListToMapMigration_should_migrate_topic_ids() = runTest {
        // Set up existing preferences with movies ids
        val preMigrationUserPreferences = userPreferences {
            deprecatedLongBookmarkedMovieIds.addAll(listOf(1, 2, 3))
        }
        // Assert that there are no movie ids in the map yet
        assertEquals(
            emptyMap<Long, Boolean>(),
            preMigrationUserPreferences.bookmarkedMovieIdsMap,
        )

        // Run the migration
        val postMigrationUserPreferences =
            ListToMapMigration.migrate(preMigrationUserPreferences)

        // Assert the deprecated movie ids have been migrated to the movie ids map
        assertEquals(
            mapOf(1L to true, 2L to true, 3L to true),
            postMigrationUserPreferences.bookmarkedMovieIdsMap,
        )

        // Assert that the migration has been marked complete
        assertTrue(postMigrationUserPreferences.hasDoneListToMapMigration)
    }
}
