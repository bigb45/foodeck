package com.example.authentication.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authentication.presentation.screens.Screen
import com.example.authentication.presentation.screens.main_screen.explore.Explore

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Explore.route) {
        composable(Screen.Explore.route) {
            Explore(navController)
        }
    }
}