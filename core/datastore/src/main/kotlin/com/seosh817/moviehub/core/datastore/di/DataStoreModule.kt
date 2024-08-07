package com.seosh817.moviehub.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.common.network.di.ApplicationScope
import com.seosh817.moviehub.core.datastore.UserPreferences
import com.seosh817.moviehub.core.datastore.AppStartUpSettingsSerializer
import com.seosh817.moviehub.core.datastore.ListToMapMigration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesAppSettingsDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(MovieHubDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        appStartUpSettingsSerializer: AppStartUpSettingsSerializer,
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = appStartUpSettingsSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
            migrations = listOf(
                ListToMapMigration,
            )
        ) {
            context.dataStoreFile("app_start_up_settings.pb")
        }
}
