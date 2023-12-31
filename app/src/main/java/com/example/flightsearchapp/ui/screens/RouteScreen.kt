package com.example.flightsearchapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.Airport
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import com.example.flightsearchapp.ui.FlightSearchUiState
import com.example.flightsearchapp.ui.FlightSearchViewModel
import com.example.flightsearchapp.ui.screens.sharedComponents.RouteCard

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AirportRouteListScreen(
    uiState: FlightSearchUiState,
    airportList: List<Airport>,
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(4.dp)
    ) {

        Text(
            text = "Flights from ${uiState.currentAirport?.iataCode?.uppercase() ?: ""}",
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(4.dp)
                .padding(top = 4.dp, bottom = 4.dp)
        )
        if (uiState.currentAirport != null) {
            LazyColumn(content = {
                items(airportList) { airport ->
                    if (airport.iataCode != uiState.currentAirport.iataCode) {
                        RouteCard(
                            originAirport = uiState.currentAirport,
                            destinationAirport = airport,
                            flightSearchViewModel = flightSearchViewModel,
                            favoriteExists = flightSearchViewModel.checkFavoriteExists(
                                uiState.currentAirport.iataCode,
                                airport.iataCode
                            ).collectAsState(initial = null).value,
                            modifier = modifier
                        )
                    }
                }
            })
        }
    }
}