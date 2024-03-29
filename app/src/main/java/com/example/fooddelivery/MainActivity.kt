package com.example.fooddelivery

import android.os.Bundle
import android.util.Log.d
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.authentication.navigation.authenticationScreen
import com.example.authentication.navigation.authRoute
import com.example.authentication.navigation.navigateToAuthentication
import com.example.fooddelivery.ui.theme.FoodDeliveryTheme
import com.example.main_screen.navigation.navigateToMainScreen
import com.example.navigation.homeRoute
import com.example.navigation.homeScreen
import com.example.navigation.navigateToHome
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
                            navController.popBackStack()

                            navController.navigateToHome()
                        },
                        onSignOut = {
                            navController.popBackStack()
                            navController.navigateToAuthentication()
                        }
                    )

                    homeScreen()


                }
            }
        }
    }
}

