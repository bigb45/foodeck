package com.example.authentication.presentation.screens.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authentication.presentation.screens.auth.LoginMethods

const val loginMethodsRoute = "login_methods_route"
fun NavController.navigateToLoginMethods(navOptions: NavOptions? = null){
    this.navigate(loginMethodsRoute)
}

fun NavGraphBuilder.loginMethods(navController: NavController){
    composable(
        route = loginMethodsRoute
    ){
//        todo: change this
        LoginMethods(navController)
    }
}