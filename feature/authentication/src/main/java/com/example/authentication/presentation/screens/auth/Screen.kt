package com.example.authentication.presentation.screens.auth

sealed class Screen(val route: String) {


    object SignInResultScreen: Screen("sign_in_result")
}