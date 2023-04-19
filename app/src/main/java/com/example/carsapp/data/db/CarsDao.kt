package com.example.carsapp.data.db

import androidx.room.*
import com.example.carsapp.data.model.CarDao

@Dao
interface CarsDao {
    @Query("SELECT * FROM cars")
    suspend fun getAll(): List<CarDao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(car: CarDao)

    @Update
    suspend fun update(car: CarDao)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cars: List<CarDao>)

    @Query("DELETE FROM cars")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(car: CarDao)
}