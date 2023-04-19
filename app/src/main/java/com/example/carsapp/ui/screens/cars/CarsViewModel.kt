package com.example.carsapp.ui.screens.cars

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsapp.domain.model.Car
import com.example.carsapp.domain.repository.SharedPreferencesRepository
import com.example.carsapp.domain.usecase.AddCarUseCase
import com.example.carsapp.domain.usecase.GetCarsUseCase
import com.example.carsapp.domain.usecase.UpdateCarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val getCarsUseCase: GetCarsUseCase,
    private val addCarUseCase: AddCarUseCase,
    private val updateCarUseCase: UpdateCarUseCase,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ViewModel() {
    private val carsState = MutableStateFlow<SnapshotStateList<Car>>(mutableStateListOf())
    val filterState = MutableStateFlow<HashMap<String, Boolean>>(HashMap())
    val filteredCarsState: MutableStateFlow<SnapshotStateList<Car>> = MutableStateFlow<SnapshotStateList<Car>>(mutableStateListOf())


    fun addCar(car: Car) {
        carsState.value.add(car)
        carsState.value.sortBy { it.make }
        filter(filterState.value)
        viewModelScope.launch {
            addCarUseCase.addCar(car)
        }
    }

    fun getMakeList(): List<String> {
        val set = HashSet<String>()
        carsState.value.forEach {
            set.add(it.make)
        }
        return ArrayList(set)
    }

    fun updateFilter(filterStateMap: HashMap<String, Boolean>) {
        viewModelScope.launch {
            filterStateMap.forEach { (k, v) ->
                sharedPreferencesRepository.putBoolean(k, v)
            }
        }
        filterState.value = filterStateMap
        filter(filterStateMap)
    }

    private fun filter(filterStateMap: HashMap<String, Boolean>) {
        viewModelScope.launch {
            val list = carsState.value.filter {
                var b = filterStateMap[it.make]
                b != false
            }
            val list2 = SnapshotStateList<Car>()
            list2.addAll(list)
            filteredCarsState.emit(list2)
        }
    }

    fun updateCar(car: Car) {
        viewModelScope.launch {
            updateCarUseCase.updateCar(car)
        }
    }

    init {
        viewModelScope.launch {
            getCarsUseCase.getCars().collectLatest { cars ->
                val list = SnapshotStateList<Car>()
                list.addAll(cars)
                list.sortBy { it.make }
                carsState.emit(list)
                val filterMap: HashMap<String, Boolean> = HashMap()
                getMakeList().forEach {
                    var b = sharedPreferencesRepository.getBoolean(it)
                    filterMap[it] = b
                }
                filter(filterMap)
                filterState.emit(filterMap)
            }
        }
    }

}