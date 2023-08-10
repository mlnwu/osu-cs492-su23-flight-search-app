package com.example.flightsearchapp.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "departure_code")
    val departureCode: String,
    @ColumnInfo(name = "destination_code")
    val destinationCode: String
)

fun Favorite.isSameAs(other: Favorite): Boolean {
    return departureCode == other.departureCode && destinationCode == other.destinationCode
}

@Entity
data class Airport(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo
    val name: String,
    @ColumnInfo(name = "iata_code")
    val iataCode: String,
    @ColumnInfo
    val passengers: Int,
)

data class FavoriteWithAirports(
    @Embedded val favorite: Favorite,
    @Relation(
        parentColumn = "departure_code",
        entityColumn = "iata_code"
    )
    val originAirport: Airport,
    @Relation(
        parentColumn = "destination_code",
        entityColumn = "iata_code"
    )
    val destinationAirport: Airport
)