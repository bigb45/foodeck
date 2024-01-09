package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.home.HomeScreen
import com.example.restaurant.RestaurantScreen

const val homeRoute = "home_route"


fun NavController.navigateToHome(navOptions: NavOptions? = null){
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(onRestaurantClick: (String) -> Unit){
    composable(
        route = homeRoute
    ){
        HomeScreen(
            onRestaurantClick = onRestaurantClick
        )
    }

    composable(
        route = "$homeRoute/{restaurantId}"
    ){ it ->

        val restaurantId = it.arguments?.getString("restaurantId")
        restaurantId?.let {
            RestaurantScreen(it)
        }
    }
}