package com.example.carsapp.domain.repository

import com.example.carsapp.domain.model.Car
import kotlinx.coroutines.flow.Flow

interface CarsRepository {
    suspend fun getCars(): Flow<List<Car>>

    suspend fun addCar(car: Car)
}