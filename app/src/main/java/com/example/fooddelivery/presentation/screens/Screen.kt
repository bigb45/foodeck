package com.example.fooddelivery.presentation.screens

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login")
    object SignupScreen: Screen("create_account")
    object MainScreen: Screen("main")
    object Explore: Screen("explore")
    object EmailLoginScreen : Screen("email_login")
}