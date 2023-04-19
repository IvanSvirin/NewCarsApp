package com.example.carsapp.domain.usecase

import com.example.carsapp.domain.model.Car
import com.example.carsapp.domain.repository.CarsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCarUseCase @Inject constructor(private val carsRepository: CarsRepository) {
    suspend fun updateCar(car: Car) = carsRepository.updateCar(car)
}