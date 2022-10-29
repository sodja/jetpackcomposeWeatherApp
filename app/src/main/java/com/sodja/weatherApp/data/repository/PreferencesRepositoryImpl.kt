package com.sodja.weatherApp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sodja.weatherApp.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {


    /**
     * Edit a String value in DataStore transactionally in an atomic read-modify-write operation.
     *
     * @param key: The name of the preference (Preferences.Key) to edit
     * @param value: The value to set the preference to
     */
    override suspend fun writeString(key: String, value: String) {
        dataStore.edit { settings ->
            val preferenceKey = stringPreferencesKey(key)
            settings[preferenceKey] = value
        }
    }

    /**
     * Read a String value in DataStore.
     *
     * @param key: The name of the preference (Preferences.Key) to edit
     * @param default: The default value to return preference value is null
     * @return String value associated to the preference
     */
    override suspend fun readString(key: String, default: String): String {
        val result = dataStore.data
            .map { preferences ->
                val preferenceKey = stringPreferencesKey(key)
                preferences[preferenceKey] ?: default
            }.first()
        return result
    }

    /**
     * Clear all data from DataStore.
     *
     */
    override suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }

    override fun getDataStore(): DataStore<Preferences> {
        return dataStore
    }
}