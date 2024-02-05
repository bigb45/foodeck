package com.example.menu_item.navigation

import android.util.Log.d
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.menu_item.MenuItemScreen
import com.example.restaurant.navigation.restaurantIdArgument
import com.example.restaurant.navigation.restaurantRoute
import java.net.URLEncoder

const val menuItemRoute = "menu_item_route"
const val menuItemIdArgument = "menuItemId"
private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

fun NavController.navigateToMenuItem(navOptions: NavOptions? = null, menuItemId: String, restaurantId: String) {
    val encodedMenuItemId = URLEncoder.encode(
        menuItemId, URL_CHARACTER_ENCODING
    )
    val encodedRestaurantId = URLEncoder.encode(
        restaurantId, URL_CHARACTER_ENCODING
    )

    d("error", menuItemId)
    this.navigate(
        route = "$restaurantRoute/$menuItemRoute/$encodedRestaurantId/$encodedMenuItemId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.menuItemScreen(onNavigateUp: () -> Unit) {
    composable(
        route = "$restaurantRoute/$menuItemRoute/{$restaurantIdArgument}/{$menuItemIdArgument}",
        arguments = listOf(
            navArgument(menuItemIdArgument) { type = NavType.StringType },
            navArgument(restaurantIdArgument) { type = NavType.StringType },
        ),
    ) {
        MenuItemScreen(onNavigateUp = onNavigateUp)
    }
}