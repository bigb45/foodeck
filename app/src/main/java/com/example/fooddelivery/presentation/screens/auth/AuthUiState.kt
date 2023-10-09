package com.example.fooddelivery.presentation.screens.auth

import com.example.fooddelivery.util.FieldError
// TODO: convert data class to sealed interface, handle different login states (success, failure, unauthorized)
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
