package com.example.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val homeRoute = "home_route"
const val mainScreenRoute = "main_screen_route"
fun NavController.navigateToHome(navOptions: NavOptions? = null){
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(){
    composable(
        route = mainScreenRoute
    ){

    }
}