package com.example.fooddelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.authentication.navigation.authenticationScreen
import com.example.authentication.navigation.loginMethodsRoute
import com.example.fooddelivery.ui.theme.FoodDeliveryTheme
import com.example.home.navigation.welcomeScreen
import com.example.home.navigation.navigateToWelcome
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

                    authenticationScreen { userId: String ->
//                          pop the all of the auth navgraph to eliminate unwanted behavior
//                          (clicking back arrow after navigating to home screen shows the auth
//                          screen momentarily)
                        navController.popBackStack()
                        navController.navigateToWelcome(userId = userId)
                    }

                    welcomeScreen()
                }
            }
        }
    }
}

