package com.example.authentication.presentation.screens.auth

sealed class Screen(val route: String) {

    object LoginScreen : Screen("login")
    object SignupScreen : Screen("create_account")

    object SignInResultScreen: Screen("sign_in_result")
}