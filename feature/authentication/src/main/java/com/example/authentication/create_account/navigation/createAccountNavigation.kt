package com.example.authentication.create_account.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.authentication.create_account.SignupRoute

const val createAccountRoute = "create_account_route"

fun NavController.navigateToCreateAccount(navOptions: NavOptions? = null) {
    this.navigate(createAccountRoute)
}

fun NavGraphBuilder.createAccountScreen(
    onNavigationIconClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
    onAuthenticationSuccess: (String) -> Unit,
) {
    composable(route = createAccountRoute) {
        SignupRoute(onNavigateUp = onNavigationIconClick, onLoginInsteadClick = onSecondaryButtonClick, onAuthenticationSuccess = onAuthenticationSuccess)
    }
}