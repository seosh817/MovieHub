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
package com.seosh817.moviehub.core.datastore.test

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.seosh817.moviehub.core.common.network.di.ApplicationScope
import com.seosh817.moviehub.core.datastore.AppStartUpSettingsSerializer
import com.seosh817.moviehub.core.datastore.UserPreferences
import com.seosh817.moviehub.core.datastore.di.DataStoreModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineScope
import org.junit.rules.TemporaryFolder
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class],
)
object TestDataStoreModule {
    @Provides
    @Singleton
    fun providesAppPreferencesDataStore(
        @ApplicationScope scope: CoroutineScope,
        appStartUpSettingsSerializer: AppStartUpSettingsSerializer,
        tmpFolder: TemporaryFolder,
    ): DataStore<UserPreferences> =
        tmpFolder.testAppPreferencesDataStore(
            coroutineScope = scope,
            appStartUpSettingsSerializer = appStartUpSettingsSerializer,
        )
}

fun TemporaryFolder.testAppPreferencesDataStore(
    coroutineScope: CoroutineScope,
    appStartUpSettingsSerializer: AppStartUpSettingsSerializer = AppStartUpSettingsSerializer(),
) = DataStoreFactory.create(
    serializer = appStartUpSettingsSerializer,
    scope = coroutineScope,
) {
    newFile("app_startup_test.pb")
}
