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
            appStartUpSettingsSerializer = appStartUpSettingsSerializer
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
