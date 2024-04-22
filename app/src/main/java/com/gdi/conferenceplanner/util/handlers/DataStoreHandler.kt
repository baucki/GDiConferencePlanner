package com.gdi.conferenceplanner.util.handlers

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object DataStoreHandler {

    val PREFERENCE_KEY = stringPreferencesKey("token")

    lateinit var dataStore: DataStore<Preferences>
    suspend fun read(): String {
        return dataStore.data.map {
            it[PREFERENCE_KEY] ?: ""
        }.first()
    }
    suspend fun write(value: String) {
            dataStore.edit { user ->
            user[PREFERENCE_KEY] = value
        }
    }
}


