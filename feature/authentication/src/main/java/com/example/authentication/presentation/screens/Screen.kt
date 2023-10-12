package com.example.authentication.presentation.screens

sealed class Screen(val route: String) {

    object LoginScreen : Screen("login")
    object SignupScreen : Screen("create_account")
    object EmailLoginScreen : Screen("email_login")
    object FacebookLoginScreen: Screen("facebook_login")
}