package com.example.carsapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carsapp.domain.model.Car
import com.google.gson.annotations.SerializedName

data class CarDto(
    @SerializedName("year")
    val year: Int,
    @SerializedName("make")
    val make: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
)

@Entity(tableName = "cars")
data class CarDao(
    @PrimaryKey(autoGenerate = true)
    var primaryKey: Int = 0,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "make")
    val make: String,
    @ColumnInfo(name = "model")
    val model: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
)

fun CarDto.mapDtoToDao() = CarDao(
    primaryKey = 0,
    year = this.year,
    make = this.make ?: "",
    model = this.model ?: "",
    imageUrl = this.imageUrl ?: "",
)

fun Car.mapToDao() = CarDao(
    primaryKey = 0,
    year = this.year,
    make = this.make ?: "",
    model = this.model ?: "",
    imageUrl = this.imageUrl ?: "",
)

