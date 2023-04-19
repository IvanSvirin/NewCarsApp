package com.example.carsapp.data.repository

import com.example.carsapp.data.db.CarsDatabase
import com.example.carsapp.data.model.mapDtoToDao
import com.example.carsapp.data.model.mapToDao
import com.example.carsapp.data.rest.CarsApiService
import com.example.carsapp.domain.model.Car
import com.example.carsapp.domain.model.mapFromDao
import com.example.carsapp.domain.repository.CarsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CarsRepositoryImpl @Inject constructor(
    private val database: CarsDatabase,
    private val carsApiService: CarsApiService
) :
    CarsRepository {
    override suspend fun getCars(): Flow<List<Car>> {
        return flow<List<Car>> {
//            emit(database.carsDao().getAll().map { it.mapFromDao() })
//            val netResult = carsApiService.getCars()
//            if (!netResult.isNullOrEmpty()) {
//                database.carsDao().deleteAll()
//                database.carsDao().insertAll(netResult.filter { it != null }.map { it.mapDtoToDao() })
//                emit(netResult.filter { it != null }.map { it.mapFromDto() })
//            }
            val dbResult = database.carsDao().getAll()
            if (!dbResult.isNullOrEmpty()) emit(dbResult.map { it.mapFromDao() })
            else {
                val netResult = carsApiService.getCars()
                if (!netResult.isNullOrEmpty()) {
                    database.carsDao().deleteAll()
                    database.carsDao()
                        .insertAll(netResult.filter { it != null }.map { it.mapDtoToDao() })
                    emit(database.carsDao().getAll().map { it.mapFromDao() })
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun addCar(car: Car) {
        withContext(Dispatchers.IO) {
            database.carsDao().insert(car.mapToDao())
        }
    }

    override suspend fun updateCar(car: Car) {
        withContext(Dispatchers.IO) {
            database.carsDao().update(car.mapToDao())
        }
    }
}