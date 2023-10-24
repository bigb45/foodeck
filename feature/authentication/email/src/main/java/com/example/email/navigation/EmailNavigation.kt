package com.example.email.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.email.EmailLogin

const val emailLoginRoute = "email_login_route"

fun NavController.navigateToEmail(navOptions: NavOptions? = null){
    this.navigate(emailLoginRoute, navOptions)
}

fun NavGraphBuilder.emailLogin(){
    composable(route = emailLoginRoute){
        EmailLogin(onNavigateUpClick = {})
    }
}