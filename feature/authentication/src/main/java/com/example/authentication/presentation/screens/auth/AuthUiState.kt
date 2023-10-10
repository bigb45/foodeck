package com.example.authentication.presentation.screens.auth

// TODO: convert data class to sealed interface, handle different login states (success, failure, unauthorized)
data class AuthUiState(

    val username: String = "",
    var email: String = "",
    val phoneNumber: String = "",
    val password: String = "",

    val usernameError: com.example.authentication.util.FieldError = com.example.authentication.util.FieldError(
        false,
        null
    ),
    val emailError: com.example.authentication.util.FieldError = com.example.authentication.util.FieldError(
        false,
        null
    ),
    val phoneNumberError: com.example.authentication.util.FieldError = com.example.authentication.util.FieldError(
        false,
        null
    ),
    val passwordError: com.example.authentication.util.FieldError = com.example.authentication.util.FieldError(
        false,
        null
    ),

    )
