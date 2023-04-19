package com.example.carsapp.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/creatifyme/2a334c00a117097bfdb47f031edf292c/raw/f92a2f28777aecf8d2fcaee9245deeb849c5a2ea/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}