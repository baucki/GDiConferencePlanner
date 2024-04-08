package com.plcoding.daggerhiltcourse.util.handlers

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreHandler {

    val PREFERENCE_KEY = stringPreferencesKey("user")

    lateinit var dataStore: DataStore<Preferences>
    fun read(): Flow<String> {
        val flow: Flow<String> = dataStore.data
            .map { user ->
                user[PREFERENCE_KEY] ?: ""
            }
        return flow
    }
    suspend fun write(value: String) {
            dataStore.edit { user ->
            user[PREFERENCE_KEY] = value
        }
    }
}


