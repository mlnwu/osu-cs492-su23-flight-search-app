package com.example.flightsearchapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearchapp.data.FlightSearchDatabase
import com.example.flightsearchapp.data.FlightSearchPreferencesDataStore

private const val FLIGHT_SEARCH_PREFERENCE_NAME = "flight_search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = FLIGHT_SEARCH_PREFERENCE_NAME
)

class FlightSearch: Application() {
    lateinit var database: FlightSearchDatabase
    lateinit var preferences: FlightSearchPreferencesDataStore

    override fun onCreate() {
        super.onCreate()
        database = FlightSearchDatabase.getDatabase(this)
        preferences = FlightSearchPreferencesDataStore(dataStore)
    }
}