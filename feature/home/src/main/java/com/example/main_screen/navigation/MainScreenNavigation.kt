package com.example.main_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.main_screen.HomeScreen
import com.example.navigation.homeRoute
import com.example.restaurant.RestaurantScreen



fun NavController.navigateToMainScreen(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(onRestaurantClick: (String) -> Unit, onNavigateUp: () -> Unit) {
    composable(
        route = homeRoute
    ) {
        HomeScreen(
            onRestaurantClick = onRestaurantClick
        )
    }

    composable(
        route = "$homeRoute/{restaurantId}"
    ) { it ->

        val restaurantId = it.arguments?.getString("restaurantId")
        restaurantId?.let {
            RestaurantScreen(restaurantId = it, onNavigateUp, {})
        }
    }
}