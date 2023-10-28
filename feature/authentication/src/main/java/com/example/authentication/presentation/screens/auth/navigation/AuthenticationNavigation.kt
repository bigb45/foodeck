package com.example.authentication.presentation.screens.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.authentication.presentation.screens.auth.Authentication
import com.example.authentication.presentation.screens.auth.screens.LoginMethods

const val loginMethodsRoute = "login_methods_route"
fun NavController.navigateToLoginMethods(navOptions: NavOptions? = null){
    this.navigate(route = loginMethodsRoute, navOptions = navOptions)
}

fun NavGraphBuilder.loginMethods(navController: NavController, onAuthenticationSuccess: (String) -> Unit){
    composable(
        route = loginMethodsRoute
    ){

        LoginMethods(navController, onGoogleAuthenticationSuccess = onAuthenticationSuccess)
    }
}

fun NavGraphBuilder.authenticationScreen(onAuthenticationSuccess: (String) -> Unit){
    composable(
        route = loginMethodsRoute
    ){
        Authentication { userId: String -> onAuthenticationSuccess(userId) }
    }
}