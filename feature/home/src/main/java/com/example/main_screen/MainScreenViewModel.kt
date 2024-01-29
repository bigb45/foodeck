package com.example.main_screen

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.listAsResult
import com.example.common.Result
import com.example.data.models.Restaurant
import com.example.domain.use_cases.GetAllRestaurantsUseCase
import com.example.domain.use_cases.GetOffersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getRestaurants: GetAllRestaurantsUseCase,
    private val getOffers: GetOffersUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState.Loading)
    val uiState: StateFlow<MainScreenUiState> = _state

    init {
        fetchRestaurantData()
    }
    fun reload(){

        fetchRestaurantData()
    }
    private fun fetchRestaurantData() {
        _state.value = MainScreenUiState.Loading
        viewModelScope.launch {
            val restaurantsFlow = getRestaurants().listAsResult()
            val offersFlow = getOffers().listAsResult()
            combine(restaurantsFlow, offersFlow){
                restaurantsResult, offersResult ->
                when {
                    restaurantsResult is Result.Error -> {
                        _state.value = MainScreenUiState.Error(restaurantsResult.exception?.message)
                    }
                    offersResult is Result.Error -> {
                        _state.value = MainScreenUiState.Error(offersResult.exception?.message)
                    }
                    restaurantsResult == Result.Loading || offersResult == Result.Loading -> {
                        _state.value = MainScreenUiState.Loading
                    }
                    restaurantsResult is Result.Success && offersResult is Result.Success -> {
                        d("error", "${restaurantsResult.data}")

                        _state.value = MainScreenUiState.Success(
                            restaurants = restaurantsResult.data,
                            offers = offersResult.data
                        )
                    }

                }
            }.collect{}
        }
    }
}




