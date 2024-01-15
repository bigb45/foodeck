package com.example.main_screen

import com.example.data.models.OffersDto
import com.example.data.models.RestaurantDto

sealed class MainScreenUiState {
    data class Success(
        val restaurants: List<RestaurantDto>,
        val offers: List<OffersDto>,

        ): MainScreenUiState()

    object Loading: MainScreenUiState()
    data class Error(val message: String?) : MainScreenUiState() {

    }
}