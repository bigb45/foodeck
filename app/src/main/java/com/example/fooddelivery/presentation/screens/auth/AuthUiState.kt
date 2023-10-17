package com.example.fooddelivery.presentation.screens.auth

import com.example.domain.data.FieldError


data class AuthUiState(

    val username: String = "",
    var email: String = "",
    val phoneNumber: String = "",
    val password: String = "",

    val usernameError: com.example.domain.data.FieldError = com.example.domain.data.FieldError(
        false,
        null
    ),
    val emailError: com.example.domain.data.FieldError = com.example.domain.data.FieldError(
        false,
        null
    ),
    val phoneNumberError: com.example.domain.data.FieldError = com.example.domain.data.FieldError(
        false,
        null
    ),
    val passwordError: com.example.domain.data.FieldError = com.example.domain.data.FieldError(
        false,
        null
    ),

    )
