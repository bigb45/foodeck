package com.example.facebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.facebook.FacebookLogin


const val facebookLoginRoute = "facebook_login_route"

fun NavController.navigateToFacebook(navOptions: NavOptions? = null){
    this.navigate(facebookLoginRoute, navOptions)
}

fun NavGraphBuilder.facebookLogin(

){
    composable(route = facebookLoginRoute){
        FacebookLogin()
    }
}