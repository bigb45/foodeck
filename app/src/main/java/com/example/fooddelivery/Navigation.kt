package com.example.fooddelivery

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.presentation.screens.Screen
import com.example.fooddelivery.presentation.screens.auth.Login
import com.example.fooddelivery.presentation.screens.auth.Signup

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
        composable(Screen.LoginScreen.route){
            Login(navController)
        }
        composable(Screen.SignupScreen.route){
            Signup(navController = navController)
        }

    }
}