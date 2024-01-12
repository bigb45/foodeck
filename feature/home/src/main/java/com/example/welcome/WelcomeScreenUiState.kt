package com.example.welcome

import com.example.data.models.UserDetailsModel

sealed interface WelcomeScreenUiState {
    object Loading: WelcomeScreenUiState

    data class Success(val user: UserDetailsModel ): WelcomeScreenUiState

}
