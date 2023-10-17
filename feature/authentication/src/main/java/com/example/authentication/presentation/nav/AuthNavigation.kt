package com.example.authentication.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.authentication.presentation.screens.auth.Screen
import com.example.authentication.presentation.screens.auth.data.AuthResult
import com.example.authentication.presentation.screens.auth.email_login.LoginViewModel
import com.example.authentication.presentation.screens.auth.signup.SignupViewModel
import com.example.authentication.presentation.screens.auth.LoginMethods
import com.example.authentication.presentation.screens.auth.SignInResult
import com.example.authentication.presentation.screens.auth.email_login.EmailLogin
import com.example.authentication.presentation.screens.auth.facebook_login.FacebookLogin
import com.example.authentication.presentation.screens.auth.facebook_login.FacebookLoginViewModel
import com.example.authentication.presentation.screens.auth.signup.Signup


@Composable
fun AuthNavigation(
//    signOut: () -> Unit,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginMethods(navController = navController)
        }

        composable(Screen.SignupScreen.route) {
            Signup(navController = navController)
        }

        composable(Screen.EmailLoginScreen.route) {
            EmailLogin(navController = navController)
        }

        composable(Screen.FacebookLoginScreen.route){
            FacebookLogin(navController = navController)
        }

        composable(Screen.SignInResultScreen.route){
            SignInResult(navController = navController)
        }

    }
}



