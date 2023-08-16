/* Assignment 5

    FlightSearchPreferencesDataStore.kt

    Maggie Wu / wumag@oregonstate.edu
    CS 492 / Oregon State University
 */

package com.example.flightsearchapp.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import android.content.ContentValues.TAG
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class FlightSearchPreferencesDataStore(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val LAST_SEARCH_QUERY = stringPreferencesKey("last_search_query")

    }

    suspend fun saveLastSearchQuery(lastSearchQuery: String) {
        Log.d(TAG, "saveLastSearchQuery: $lastSearchQuery")

        dataStore.edit { preferences ->
            preferences[LAST_SEARCH_QUERY] = lastSearchQuery
        }
    }

    val lastSearchQuery: Flow<String> =
        dataStore.data
            .catch {
                if (it is IOException) {
                    Log.e(TAG, "Error reading preferences", it)
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map { preferences ->
                preferences[LAST_SEARCH_QUERY] ?: ""
            }
}