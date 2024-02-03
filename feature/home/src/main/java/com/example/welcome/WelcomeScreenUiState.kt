package com.example.welcome

import com.example.data.models.UserDetails

sealed interface WelcomeScreenUiState {
    object Loading: WelcomeScreenUiState

    data class Success(val user: UserDetails ): WelcomeScreenUiState

}
