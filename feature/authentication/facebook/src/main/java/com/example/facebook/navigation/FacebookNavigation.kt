package com.example.facebook.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.facebook.FacebookLogin


const val facebookLoginRoute = "facebook_login_route"

fun NavController.navigateToFacebook(navOptions: NavOptions? = null) {
    this.navigate(facebookLoginRoute, navOptions)
}

fun NavGraphBuilder.facebookLoginScreen(
    onContinueClick: () -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    composable(route = facebookLoginRoute) {
        FacebookLogin(
            onContinueClick = onContinueClick,
            onNavigationIconClick = onNavigationIconClick
        )
    }
}