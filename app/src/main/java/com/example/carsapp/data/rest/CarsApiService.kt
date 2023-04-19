package com.example.carsapp.data.rest

import com.example.carsapp.data.model.CarDto
import retrofit2.http.GET

interface CarsApiService {

    @GET("cars.json")
    suspend fun getCars() : List<CarDto>?
}