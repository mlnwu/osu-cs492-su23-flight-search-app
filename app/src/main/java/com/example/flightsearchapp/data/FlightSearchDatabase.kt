package com.example.flightsearchapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Airport::class, Favorite::class],  version = 1, exportSchema = false)
abstract class FlightSearchDatabase: RoomDatabase() {

    abstract fun flightSearchDao(): FlightSearchDao
    companion object {
        @Volatile
        private var Instance: FlightSearchDatabase? = null

        fun getDatabase(context: Context): FlightSearchDatabase {
            return Instance ?: synchronized(this) {

                val dbFile = context.getDatabasePath("flight_search_database")

                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    FlightSearchDatabase::class.java,
                    "flight_search_database"
                ).let { builder ->
                    if (dbFile.exists()) {
                        builder.fallbackToDestructiveMigration()
                    } else {
                        builder.createFromAsset("flight_search.db")
                    }
                }

                builder.build().also { Instance = it }
            }
        }
    }
}
