package com.example.carsapp.domain.model

import com.example.carsapp.data.model.CarDao
import com.example.carsapp.data.model.CarDto

data class Car(
    var primaryKey: Int = 0,
    var year: Int,
    var make: String,
    var model: String,
    var imageUrl: String,
)

fun CarDao.mapFromDao() = Car(
    this.primaryKey,
    this.year,
    this.make,
    this.model,
    this.imageUrl,
)
