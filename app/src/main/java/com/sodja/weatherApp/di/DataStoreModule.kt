package com.sodja.weatherApp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.sodja.weatherApp.commons.Constants.DATA_STORE_NAME
import com.sodja.weatherApp.data.repository.PreferencesRepositoryImpl
import com.sodja.weatherApp.domain.repository.PreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {

    @Binds
    fun bindPreferencesRepository(
        preferencesRepository: PreferencesRepositoryImpl
    ): PreferencesRepository

    companion object {
        @Singleton
        @Provides
        fun dataStore(@ApplicationContext context: Context): DataStore<Preferences> =
            PreferenceDataStoreFactory.create(
                migrations = listOf(),
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
            ) {
                context.preferencesDataStoreFile(DATA_STORE_NAME)
            }
    }
}
