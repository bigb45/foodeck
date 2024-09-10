package com.example.main_screen

import com.example.data.models.BentoSectionData
import com.example.data.models.Offer
import com.example.data.models.Restaurant

sealed class MainScreenUiState {
    data class Success(
        val restaurants: List<Restaurant>,
        val offers: List<Offer>,
        val bentoSection: List<BentoSectionData>
        ): MainScreenUiState()

    object Loading: MainScreenUiState()
    data class Error(val message: String?) : MainScreenUiState()
}