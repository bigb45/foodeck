package com.example.authentication.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authentication.presentation.screens.Screen
import com.example.authentication.presentation.screens.auth.email_login.LoginViewModel
import com.example.authentication.presentation.screens.auth.signup.SignupViewModel
import com.example.authentication.presentation.screens.auth.LoginMethods
import com.example.authentication.presentation.screens.auth.SignInState
import com.example.authentication.presentation.screens.auth.email_login.EmailLogin
import com.example.fooddelivery.presentation.screens.auth.signup.Signup
import kotlinx.coroutines.Job


@Composable
fun Navigation(
    signupViewModel: SignupViewModel,
    loginViewModel: LoginViewModel,
    onSignInClick: () -> Unit,
    state: SignInState
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginMethods(navController, onSignInClick, state)
        }
        composable(Screen.SignupScreen.route) {
            Signup(navController = navController, viewModel = signupViewModel)
        }
        composable(Screen.EmailLoginScreen.route) {
            EmailLogin(navController = navController, loginViewModel)
        }

    }
}

