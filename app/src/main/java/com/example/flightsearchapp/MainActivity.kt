package com.example.flightsearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme
import com.example.flightsearchapp.ui.FlightSearchRootView
import com.example.flightsearchapp.ui.FlightSearchViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlightSearchAppTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    FlightSearchRootView(
                        flightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory))
                }
            }
        }
    }
}
