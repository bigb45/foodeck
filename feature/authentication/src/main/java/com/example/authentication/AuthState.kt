package com.example.authentication

import com.example.data.models.UserDetailsModel

sealed interface AuthResult {
    object Loading : AuthResult
    object Cancelled : AuthResult
    object SignedOut : AuthResult
    data class Error(
        val errorMessage: String,
    ) : AuthResult

    data class Success(
        val data: UserDetailsModel
    ) : AuthResult

}

