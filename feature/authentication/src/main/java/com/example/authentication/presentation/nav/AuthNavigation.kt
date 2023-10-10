package com.example.authentication.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authentication.presentation.screens.Screen
import com.example.authentication.presentation.screens.auth.LoginMethods
import com.example.authentication.presentation.screens.auth.email_login.EmailLogin
import com.example.authentication.presentation.screens.auth.email_login.LoginViewModel
import com.example.authentication.presentation.screens.auth.signup.Signup
import com.example.authentication.presentation.screens.auth.signup.SignupViewModel


@Composable
fun Navigation(signupViewModel: SignupViewModel, loginViewModel: LoginViewModel) {
    val navController = rememberNavController()
//    val viewModel = SignupViewModel(EmailValidationUseCase())
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginMethods(navController)
        }
        composable(Screen.SignupScreen.route) {
            Signup(navController = navController, viewModel = signupViewModel)
        }
        composable(Screen.EmailLoginScreen.route) {
            EmailLogin(navController = navController, loginViewModel)
        }

    }
}

