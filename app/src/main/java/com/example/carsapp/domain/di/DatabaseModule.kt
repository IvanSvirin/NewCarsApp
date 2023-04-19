package com.example.carsapp.domain.di

import android.content.Context
import androidx.room.Room
import com.example.carsapp.data.db.CarsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CarsDatabase =
        Room.databaseBuilder(
            context, CarsDatabase::class.java,
            "cars_db"
        )
            .fallbackToDestructiveMigration()
            .build()
}