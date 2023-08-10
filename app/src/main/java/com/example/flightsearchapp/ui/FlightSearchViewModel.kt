package com.example.flightsearchapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import com.example.flightsearchapp.FlightSearchApp
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.Favorite
import com.example.flightsearchapp.data.FavoriteWithAirports
import com.example.flightsearchapp.data.FlightSearchDao
import com.example.flightsearchapp.data.FlightSearchPreferencesDataStore

class FlightSearchViewModel(
    private val flightSearchDao: FlightSearchDao,
    private val flightSearchPreferencesDataStore: FlightSearchPreferencesDataStore
): ViewModel() {
    private val _uiState = MutableStateFlow(FlightSearchUiState())
    val uiState: StateFlow<FlightSearchUiState> = _uiState

    private val _airportList = MutableStateFlow<List<Airport>>(emptyList())
    val airportList: StateFlow<List<Airport>> = _airportList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val airports = flightSearchDao.getAllAirports().first()
            _airportList.emit(airports)

            val lastSearchQuery = flightSearchPreferencesDataStore.lastSearchQuery.first()

            _uiState.emit(
                if (lastSearchQuery == "") {
                    _uiState.value.copy(search = null)
                } else {
                    _uiState.value.copy(search = lastSearchQuery)
                }
            )
        }
    }

    fun setFocusState(isFocused: Boolean) {
        _uiState.value = _uiState.value.copy(isFocused = isFocused)
    }


    fun setSearchQuery(searchTerm: String) {
        setPreferencesSearchQuery(searchTerm)

        if (searchTerm == "") {
            setUiSearchQuery(null)
        } else {
            setUiSearchQuery(searchTerm)
        }
    }

    fun setUiSearchQuery(searchQuery: String?) {
        _uiState.value = FlightSearchUiState(search = searchQuery, currentAirport = null)
    }

    fun setPreferencesSearchQuery(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            flightSearchPreferencesDataStore.saveLastSearchQuery(searchQuery)
        }
    }


    fun setCurrentAirport(airport: Airport) {
        _uiState.value = _uiState.value.copy(currentAirport = airport)
    }

    fun clearCurrentAirport() {
        _uiState.value = _uiState.value.copy(currentAirport = null)
    }

    fun searchAirports(search: String): Flow<List<Airport>> {
        return flightSearchDao.searchAirports(search)
    }

    fun getAllFavorites(): Flow<List<FavoriteWithAirports>> {
        return flightSearchDao.getAllFavorites()
    }

    fun checkFavoriteExists(departureCode: String, destinationCode: String): Flow<Favorite?> {
        // convert Flow<Int> to Boolean
        return flightSearchDao.favoriteExists(departureCode, destinationCode)
    }

    fun addFavorite(newFavorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("FlightSearchViewModel", "addFavorite: $newFavorite")
            flightSearchDao.addFavorite(newFavorite)
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("FlightSearchViewModel", "deleteFavorite: $favorite")
            flightSearchDao.deleteFavorite(favorite)
        }
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApp)
                FlightSearchViewModel(
                    application.database.flightSearchDao(),
                    application.preferences
                )
            }
        }
    }
}


data class FlightSearchUiState(
    val search: String? = null,
    val currentAirport: Airport? = null,
    val isFocused: Boolean = false
)