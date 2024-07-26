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
