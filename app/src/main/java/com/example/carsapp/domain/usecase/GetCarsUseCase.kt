package com.example.carsapp.domain.usecase

import com.example.carsapp.domain.model.Car
import com.example.carsapp.domain.repository.CarsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCarsUseCase @Inject constructor(private val carsRepository: CarsRepository) {
    suspend fun getCars(): Flow<List<Car>> {
        return carsRepository.getCars()
    }
}