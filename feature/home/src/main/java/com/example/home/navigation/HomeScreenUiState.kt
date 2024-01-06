package com.example.home.navigation

import com.example.data.models.RestaurantDto

sealed class HomeScreenUiState {
    data class Success(
        val restaurants: List<RestaurantDto>
    ): HomeScreenUiState()

    object Loading: HomeScreenUiState()
}