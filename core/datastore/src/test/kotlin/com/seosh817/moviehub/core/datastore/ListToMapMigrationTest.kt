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
