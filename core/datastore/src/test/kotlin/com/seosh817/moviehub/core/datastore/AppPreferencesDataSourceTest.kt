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

import com.seosh817.moviehub.core.datastore.impl.AppPreferencesDataSourceImpl
import com.seosh817.moviehub.core.datastore.source.AppPreferencesDataSource
import com.seosh817.moviehub.core.datastore.test.testAppPreferencesDataStore
import com.seosh817.moviehub.core.model.DarkThemeMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppPreferencesDataSourceTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var dataSource: AppPreferencesDataSource

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        dataSource = AppPreferencesDataSourceImpl(
            tmpFolder.testAppPreferencesDataStore(testScope),
        )
    }

    @Test
    fun shouldUseDarkThemeFalseByDefault() = testScope.runTest {
        assertEquals(DarkThemeMode.DARK, dataSource.userSettings.first().darkThemeMode)
    }

    @Test
    fun shouldUseDarkThemeIsFollowSystemWhenSet() = testScope.runTest {
        dataSource.setDarkThemeMode(DarkThemeMode.FOLLOW_SYSTEM)
        assertEquals(DarkThemeMode.FOLLOW_SYSTEM, dataSource.userSettings.first().darkThemeMode)
    }

    @Test
    fun shouldUseDynamicColorFalseByDefault() = testScope.runTest {
        assertFalse(dataSource.userSettings.first().useDynamicColor)
    }

    @Test
    fun userShouldUseDynamicColorIsTrueWhenSet() = testScope.runTest {
        dataSource.setDynamicColorPreference(true)
        assertTrue(dataSource.userSettings.first().useDynamicColor)
    }
}
