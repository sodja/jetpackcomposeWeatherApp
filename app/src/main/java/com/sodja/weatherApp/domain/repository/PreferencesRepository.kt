package com.sodja.weatherApp.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface PreferencesRepository {

    /**
     * Edit a String value in DataStore transactionally in an atomic read-modify-write operation.
     *
     * @param key: The name of the preference (Preferences.Key) to edit
     * @param value: The value to set the preference to
     */
    suspend fun writeString(key: String, value: String)


    /**
     * Read a String value in DataStore.
     *
     * @param key: The name of the preference (Preferences.Key) to edit
     * @param default: The default value to return if preference value is null
     * @return String value associated to the preference
     */
    suspend fun readString(key: String, default: String = ""): String

    /**
     * Clear all data from DataStore.
     *
     */
    suspend fun clearDataStore()


    /**
     * Get the associated DataStore Object.
     *
     */
    fun getDataStore(): DataStore<Preferences>


}