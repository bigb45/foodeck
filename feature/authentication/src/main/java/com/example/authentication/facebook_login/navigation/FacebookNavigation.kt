package com.example.authentication.facebook_login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.authentication.facebook_login.FacebookLogin


const val facebookLoginRoute = "facebook_login_route"

fun NavController.navigateToFacebook(navOptions: NavOptions? = null) {
    this.navigate(facebookLoginRoute, navOptions)
}

fun NavGraphBuilder.facebookLoginScreen(
    onContinueClick: () -> Unit,
    onNavigationIconClick: () -> Unit,
    onAuthenticationSuccess: (String) -> Unit,
) {
    composable(route = facebookLoginRoute) {
        FacebookLogin(
            onContinueClick = onContinueClick,
            onAuthenticationSuccess = onAuthenticationSuccess,
            onNavigationIconClick = onNavigationIconClick
        )
    }
}