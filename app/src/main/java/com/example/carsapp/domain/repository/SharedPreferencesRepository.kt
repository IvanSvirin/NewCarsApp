package com.example.carsapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SharedPreferencesRepository {
    fun getBoolean(key: String): Boolean

    suspend fun putBoolean(key: String, value: Boolean)
}