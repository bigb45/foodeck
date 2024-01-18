package com.example

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.main_screen.navigation.mainScreen
import com.example.menu_item.MenuItemScreen
import com.example.menu_item.navigation.menuItemScreen
import com.example.menu_item.navigation.navigateToMenuItem
import com.example.navigation.mainScreenRoute
import com.example.restaurant.navigation.navigateToRestaurant
import com.example.restaurant.navigation.restaurant

@Composable
fun Home() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = mainScreenRoute) {
        mainScreen(
            onRestaurantClick = { restaurantId ->
                navController.navigateToRestaurant(restaurantId = restaurantId)
            },
            onNavigateUp = {
                navController.popBackStack()
            },
        )

        restaurant(onNavigateUp = navController::navigateUp
        ) {
            navController.navigateToMenuItem(menuItemId = it)
        }

        menuItemScreen(
            onNavigateUp = navController::navigateUp
        )

    }
}
