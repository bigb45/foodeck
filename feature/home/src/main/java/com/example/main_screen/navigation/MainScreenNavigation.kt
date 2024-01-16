package com.example.main_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.main_screen.MainScreen
import com.example.navigation.mainScreenRoute
import com.example.restaurant.RestaurantScreen


fun NavController.navigateToMainScreen(navOptions: NavOptions? = null) {
    this.navigate(mainScreenRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(onRestaurantClick: (String) -> Unit, onNavigateUp: () -> Unit) {
    composable(
        route = mainScreenRoute
    ) {
        MainScreen(
            onRestaurantClick = onRestaurantClick
        )
    }
}