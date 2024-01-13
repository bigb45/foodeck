package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ListAsResult
import com.example.common.Result
import com.example.domain.use_cases.GetAllRestaurantsUseCase
import com.example.domain.use_cases.GetOffersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRestaurants: GetAllRestaurantsUseCase,
    private val getOffers: GetOffersUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState.Loading)
    val uiState: StateFlow<HomeScreenUiState> = _state

    init {
        fetchRestaurantData()
    }

    private fun fetchRestaurantData() {
        viewModelScope.launch {
            val restaurantsFlow = getRestaurants().ListAsResult()
            val offersFlow = getOffers().ListAsResult()
            combine(restaurantsFlow, offersFlow){
                restaurantsResult, offersResult ->
                when {
                    restaurantsResult is Result.Error -> {
                        _state.value = HomeScreenUiState.Error(restaurantsResult.exception?.message)
                    }
                    offersResult is Result.Error -> {
                        _state.value = HomeScreenUiState.Error(offersResult.exception?.message)
                    }
                    restaurantsResult == Result.Loading || offersResult == Result.Loading -> {
                        _state.value = HomeScreenUiState.Loading
                    }
                    restaurantsResult is Result.Success && offersResult is Result.Success -> {
                        _state.value = HomeScreenUiState.Success(
                            restaurants = restaurantsResult.data,
                            offers = offersResult.data
                        )
                    }
                }
            }.collect{}
        }
    }
}




