package com.example.carsapp.data.repository

import android.content.SharedPreferences
import com.example.carsapp.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SharedPreferencesRepository {


    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, true) ?: true
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }
}
