package com.example.carsapp.domain.model

import com.example.carsapp.data.model.CarDao
import com.example.carsapp.data.model.CarDto

data class Car(
    val year: Int,
    val make: String,
    val model: String,
    val imageUrl: String,
)

fun CarDao.mapFromDao() = Car(
    this.year,
    this.make,
    this.model,
    this.imageUrl,
)

fun CarDto.mapFromDto() = Car(
    this.year,
    this.make ?: "",
    this.model ?: "",
    this.imageUrl ?: "",
)
