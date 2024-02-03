package com.example.authentication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.authentication.Authentication
import com.example.authentication.LoginMethods
import com.example.authentication.create_account.navigation.navigateToCreateAccount
import com.example.authentication.email_login.navigation.navigateToEmail
import com.example.authentication.facebook_login.navigation.navigateToFacebook

// TODO: move these to the constants file
const val authRoute = "auth_route"
const val loginMethodsRoute = "login_methods_route"
fun NavController.navigateToAuthentication(navOptions: NavOptions? = null){
    this.navigate(route = authRoute, navOptions = navOptions)
}


fun NavGraphBuilder.authenticationScreen(onAuthenticationSuccess: (String) -> Unit){
    composable(
        route = authRoute
    ){
        Authentication { userId: String -> onAuthenticationSuccess(userId) }
    }
}

internal fun NavGraphBuilder.loginMethods(navController: NavController, onAuthenticationSuccess: (String) -> Unit){
    composable(
        route = loginMethodsRoute
    ){
        LoginMethods(
            onAuthenticationSuccess = onAuthenticationSuccess,
            navigateToEmail = navController::navigateToEmail,
            navigateToCreateAccount = navController::navigateToCreateAccount,
            navigateToFacebook = navController::navigateToFacebook
        )
    }
}