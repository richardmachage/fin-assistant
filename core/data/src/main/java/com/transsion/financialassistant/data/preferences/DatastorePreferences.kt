package com.transsion.financialassistant.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "financialassistant.DATASTORE")

class DatastorePreferences(private val context: Context) {

    companion object {
        val MPESA_NUMBER_KEY = stringPreferencesKey("mpesa_number_key")
        val LANGUAGE_KEY = stringPreferencesKey("language_key")
    }


    suspend fun <T> saveValue(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }


    fun <T> getValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    suspend fun <T> deleteValue(key: Preferences.Key<T>) {
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}