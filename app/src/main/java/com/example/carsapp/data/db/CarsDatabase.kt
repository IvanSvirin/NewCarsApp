package com.example.carsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.carsapp.data.model.CarDao

@Database(
    entities = [CarDao::class],
    version = 1,
    exportSchema = false
)
abstract class CarsDatabase : RoomDatabase() {
    abstract fun carsDao(): CarsDao
}

