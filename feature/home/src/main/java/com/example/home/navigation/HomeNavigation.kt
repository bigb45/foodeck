package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.home.welcome.Welcome


const val homeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null){
    this.navigate(route = homeRoute, navOptions = navOptions)
}

fun NavGraphBuilder.homeScreen(){
    composable(
        route = homeRoute,
    ){
        Welcome(userId = "test")
    }
}