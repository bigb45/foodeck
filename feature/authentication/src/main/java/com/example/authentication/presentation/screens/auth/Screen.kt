package com.example.authentication.presentation.screens.auth

sealed class Screen(val route: String) {

    object LoginScreen : Screen("login")
    object SignupScreen : Screen("create_account")
    object EmailLoginScreen : Screen("email_login")
    object FacebookLoginScreen: Screen("facebook_login")
    object SignInResultScreen: Screen("sign_in_result")
}