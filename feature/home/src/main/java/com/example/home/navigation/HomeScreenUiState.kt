package com.example.home.navigation

import com.example.data.models.OffersDto
import com.example.data.models.RestaurantDto

sealed class HomeScreenUiState {
    data class Success(
        val restaurants: List<RestaurantDto>,
        val offers: List<OffersDto>,

        ): HomeScreenUiState()

    object Loading: HomeScreenUiState()
    data class Error(val message: String?) : HomeScreenUiState() {

    }
}