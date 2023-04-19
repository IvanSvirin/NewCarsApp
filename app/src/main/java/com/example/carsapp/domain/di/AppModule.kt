package com.example.carsapp.domain.di

import android.content.SharedPreferences
import com.example.carsapp.data.db.CarsDatabase
import com.example.carsapp.data.repository.CarsRepositoryImpl
import com.example.carsapp.data.repository.SharedPreferencesRepositoryImpl
import com.example.carsapp.data.rest.CarsApiService
import com.example.carsapp.domain.repository.CarsRepository
import com.example.carsapp.domain.repository.SharedPreferencesRepository
import com.example.carsapp.domain.usecase.AddCarUseCase
import com.example.carsapp.domain.usecase.GetCarsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCarsRepository(
        database: CarsDatabase,
        carsApiService: CarsApiService
    ): CarsRepository =
        CarsRepositoryImpl(database, carsApiService)

    @Provides
    @Singleton
    fun provideCarsApiService(
        retrofit: Retrofit,
    ): CarsApiService =
        retrofit.create(CarsApiService::class.java)

    @Provides
    @Singleton
    fun provideGetCarsUseCase(
        carsRepository: CarsRepository
    ): GetCarsUseCase =
        GetCarsUseCase(carsRepository)

    @Provides
    @Singleton
    fun provideAddCarUseCase(
        carsRepository: CarsRepository
    ): AddCarUseCase =
        AddCarUseCase(carsRepository)

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(
        sharedPreferences: SharedPreferences,
    ): SharedPreferencesRepository =
        SharedPreferencesRepositoryImpl(sharedPreferences)

}