package com.example

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.main_screen.navigation.mainScreen
import com.example.menu_item.navigation.menuItemScreen
import com.example.menu_item.navigation.navigateToMenuItem
import com.example.navigation.mainScreenRoute
import com.example.restaurant.navigation.navigateToRestaurant
import com.example.restaurant.navigation.restaurant

@Composable
fun Home() {
    val navController = rememberNavController()

    FoodDeliveryTheme {
        NavHost(navController = navController, startDestination = mainScreenRoute) {
            mainScreen(
                onRestaurantClick = { restaurantId ->
                    navController.navigateToRestaurant(restaurantId = restaurantId)
                },
                onNavigateUp = {
                    navController.popBackStack()
                },
            )

            restaurant(
                onNavigateUp = navController::navigateUp
            ) { menuId, restaurantId ->
                navController.navigateToMenuItem(menuItemId = menuId, restaurantId = restaurantId)
            }

            menuItemScreen(
                onNavigateUp = navController::navigateUp
            )

        }
    }
}
