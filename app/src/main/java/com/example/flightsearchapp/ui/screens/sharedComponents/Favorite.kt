package com.example.flightsearchapp.ui.screens.sharedComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.R


@Composable
fun FavoriteHeart(
    onClick: () -> Unit,
    favorited: Boolean,
    modifier: Modifier = Modifier,
) {
    TextButton(onClick = onClick) {
        if (favorited) {
            Image(
                painter = painterResource(id = R.drawable.heart),
                contentDescription = "Favorited",
                modifier = modifier.size(25.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.unfilled_heart),
                contentDescription = "Not favorited",
                modifier = modifier.size(25.dp)
            )
        }
    }
}