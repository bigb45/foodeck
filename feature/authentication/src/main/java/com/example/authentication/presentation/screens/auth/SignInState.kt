package com.example.authentication.presentation.screens.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)