package com.example.flightsearchapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import com.example.flightsearchapp.ui.FlightSearchUiState
import com.example.flightsearchapp.ui.FlightSearchViewModel
import com.example.flightsearchapp.ui.screens.sharedComponents.RouteCard


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AllFavoritesScreen(
    uiState: FlightSearchUiState,
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    val favorites = flightSearchViewModel.getAllFavorites().collectAsState(initial = emptyList()).value
    Text(
        text = "Favorite routes",
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(8.dp)
            .padding(top = 4.dp, bottom = 4.dp)
    )

    LazyColumn(content = {
        items(favorites) { favorite ->
            RouteCard(
                originAirport = favorite.originAirport,
                destinationAirport = favorite.destinationAirport,
                flightSearchViewModel = flightSearchViewModel,
                favoriteExists = favorite.favorite,
                modifier = modifier
            )
        }
    })
}