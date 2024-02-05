package com.example.restaurant.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.restaurant.RestaurantScreen
import java.net.URLEncoder

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

const val restaurantRoute = "restaurant_route"
internal const val restaurantIdArgument = "restaurantId"

fun NavController.navigateToRestaurant(navOptions: NavOptions? = null, restaurantId: String){
    val encodedRestaurantId = URLEncoder.encode(restaurantId, URL_CHARACTER_ENCODING)
    this.navigate(route = "$restaurantRoute/$encodedRestaurantId", navOptions = navOptions)
}

fun NavGraphBuilder.restaurant(onNavigateUp: () -> Unit, onItemClick: (String, String) -> Unit){

    composable(
        route = "$restaurantRoute/{$restaurantIdArgument}",
        arguments = listOf(
            navArgument(restaurantIdArgument) { type = NavType.StringType },
        ),
    ) {
            RestaurantScreen(onNavigateUp = onNavigateUp, onItemClick = {menuId, restaurantId ->
                onItemClick(menuId, restaurantId) })
    }

}