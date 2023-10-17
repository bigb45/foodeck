package com.example.fooddelivery.presentation.screens.auth

import com.example.authentication.presentation.screens.auth.data.FieldError


data class AuthUiState(

    val username: String = "",
    var email: String = "",
    val phoneNumber: String = "",
    val password: String = "",

    val usernameError: FieldError = FieldError(false, null),
    val emailError: FieldError = FieldError(false, null),
    val phoneNumberError: FieldError = FieldError(false, null),
    val passwordError: FieldError = FieldError(false, null),

    )
