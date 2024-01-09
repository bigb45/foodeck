package com.example.fooddelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.authentication.navigation.authenticationScreen
import com.example.authentication.navigation.loginMethodsRoute
import com.example.authentication.navigation.navigateToLoginMethods
import com.example.fooddelivery.ui.theme.FoodDeliveryTheme
import com.example.home.navigation.homeRoute
import com.example.home.navigation.homeScreen
import com.example.home.navigation.navigateToHome
import com.example.welcome.navigation.navigateToWelcome
import com.example.welcome.navigation.welcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            FoodDeliveryTheme {
                NavHost(startDestination = homeRoute, navController = navController) {

//                   splashScreen()

                    authenticationScreen { userId: String ->
//                          pop the all of the auth navGraph to eliminate unwanted behavior
//                          (clicking back arrow after navigating to home screen shows the auth
//                          screen momentarily)
                        navController.popBackStack()
                        navController.navigateToWelcome(userId = userId)
                    }

                    welcomeScreen(
                        onContinueClick = {
//                                          TODO: navigate to the home page
                            navController.navigateToHome()
                        },
                        onSignOut = {
                            navController.popBackStack()
                            navController.navigateToLoginMethods(
                            )
                        }
                    )

                    homeScreen(onRestaurantClick = {
                        navController.navigate("$homeRoute/$it")
                    })
                }
            }
        }
    }
}

