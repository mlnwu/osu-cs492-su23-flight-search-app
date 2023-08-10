package com.example.flightsearchapp.ui.screens.sharedComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.Favorite
import com.example.flightsearchapp.ui.FlightSearchViewModel


@Composable
fun RouteCard(
    originAirport: Airport,
    destinationAirport: Airport,
    flightSearchViewModel: FlightSearchViewModel,
    favoriteExists: Favorite?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth(0.8f)
            ) {
                Text(stringResource(id = R.string.depart))
                Row {
                    Text(
                        text = originAirport.iataCode.uppercase(),
                        modifier = modifier.padding(end = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(originAirport.name)
                }
                Text(
                    stringResource(id = R.string.arrive),
                    modifier = modifier.padding(top = 8.dp)
                )
                Row {
                    Text(
                        text = destinationAirport.iataCode.uppercase(),
                        modifier = modifier.padding(end = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(destinationAirport.name)
                }
            }
            FavoriteButton(
                onClick = {
                    if (favoriteExists != null) {
                        flightSearchViewModel.deleteFavorite(favoriteExists)
                    } else {
                        val newFavorite = Favorite(
                            departureCode = originAirport.iataCode,
                            destinationCode = destinationAirport.iataCode
                        )
                        flightSearchViewModel.addFavorite(newFavorite)
                    }
                },
                favorited = favoriteExists != null,
                modifier = modifier
            )
        }
    }
}