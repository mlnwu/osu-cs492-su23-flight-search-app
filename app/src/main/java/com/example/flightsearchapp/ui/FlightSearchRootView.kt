package com.example.flightsearchapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import com.example.flightsearchapp.ui.screens.AirportRouteListScreen
import com.example.flightsearchapp.ui.screens.AirportSearchScreen
import com.example.flightsearchapp.ui.screens.AllFavoritesScreen
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchRootView(
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState = flightSearchViewModel.uiState.collectAsState().value
    val airportList = flightSearchViewModel.airportList.collectAsState().value
    FlightSearchAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Flight Search") },
                )
            },
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
        ) { innerPadding ->
            Column(
                modifier = modifier.padding(innerPadding)
            ) {
                FlightSearchBar(
                    flightSearchViewModel = flightSearchViewModel,
                    uiState = uiState,
                )
                if (uiState.currentAirport != null) {
                    AirportRouteListScreen(
                        uiState = uiState,
                        airportList = airportList,
                        flightSearchViewModel = flightSearchViewModel,
                        modifier = modifier
                    )
                } else if (uiState.search != null) {
                    AirportSearchScreen(
                        uiState = uiState,
                        airportList = airportList,
                        flightSearchViewModel = flightSearchViewModel,
                        modifier = modifier
                    )
                } else {
                    AllFavoritesScreen(
                        uiState = uiState,
                        flightSearchViewModel,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchBar(
    flightSearchViewModel: FlightSearchViewModel,
    uiState: FlightSearchUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = uiState.search ?: "",
            onValueChange = {
                flightSearchViewModel.setSearchQuery(it)
            },
            modifier = modifier,
            shape = MaterialTheme.shapes.small,
            label = { Text("Enter airport code or name") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )
        TextButton(
            onClick = {
                flightSearchViewModel.setSearchQuery("")
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text("Clear")
        }
    }
}

