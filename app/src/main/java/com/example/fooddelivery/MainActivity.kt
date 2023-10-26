package com.example.fooddelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.authentication.presentation.screens.auth.navigation.authenticationScreen
import com.example.authentication.presentation.screens.auth.navigation.loginMethodsRoute
import com.example.fooddelivery.ui.theme.FoodDeliveryTheme
import com.example.home.navigation.homeScreen
import com.example.home.navigation.navigateToHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            FoodDeliveryTheme {
                NavHost(startDestination = loginMethodsRoute, navController = navController){

//                   splashScreen()

                    authenticationScreen(
                        onAuthenticationSuccess = {
//                          pop the backstack to eliminate unwanted behavior
//                          (clicking back arrow after navigating to home screen shows the auth
//                          screen momentarily)
                            navController.popBackStack()
                            navController.navigateToHome() }
                    )

                    homeScreen()
                }
            }
        }
    }
}

