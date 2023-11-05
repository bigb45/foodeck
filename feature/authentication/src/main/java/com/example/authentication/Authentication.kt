package com.example.authentication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.authentication.create_account.navigation.createAccountScreen
import com.example.authentication.create_account.navigation.navigateToCreateAccount
import com.example.authentication.email_login.navigation.emailLoginScreen
import com.example.authentication.email_login.navigation.navigateToEmail
import com.example.authentication.facebook_login.navigation.facebookLoginScreen
import com.example.authentication.navigation.loginMethods
import com.example.authentication.navigation.loginMethodsRoute
import com.example.authentication.navigation.navigateToLoginMethods


@Composable
fun Authentication(onAuthenticationSuccess: (String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController, startDestination = loginMethodsRoute
        ) {

            createAccountScreen(
                onNavigationIconClick = navController::navigateUp,
                onAuthenticationSuccess = onAuthenticationSuccess,
                onSecondaryButtonClick = {
//                    doing this prevents creating a 'navigation circle'
//                    between 'log in' and 'sign up' pages
                    navController.navigateUp()
                    navController.navigateToEmail()
                })

            emailLoginScreen(
                onNavigationIconClick = navController::navigateUp,
                onLoginSuccess = onAuthenticationSuccess,
                onSecondaryButtonClick = {
                    navController.navigateUp()
                    navController.navigateToCreateAccount()
                })

            facebookLoginScreen(
                onNavigationIconClick = navController::navigateUp,
                onAuthenticationSuccess = onAuthenticationSuccess,
                onContinueClick = navController::navigateToLoginMethods
            )

            loginMethods(navController, onAuthenticationSuccess)

        }
    }
}
