package com.example.authentication.email_login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.authentication.email_login.EmailLoginRoute

const val emailLoginRoute = "email_login_route"

fun NavController.navigateToEmail(navOptions: NavOptions? = null) {
    this.navigate(emailLoginRoute, navOptions)
}

fun NavGraphBuilder.emailLoginScreen(
    onNavigationIconClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    composable(route = emailLoginRoute) {
        EmailLoginRoute(
            onNavigateUpClick = onNavigationIconClick,
            onSecondaryButtonClick = onSecondaryButtonClick,
            onLoginSuccess = onLoginSuccess
        )
    }
}