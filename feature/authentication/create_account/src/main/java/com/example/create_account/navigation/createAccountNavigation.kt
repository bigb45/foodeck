package com.example.create_account.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.create_account.Signup

const val createAccountRoute = "create_account_route"

fun NavController.navigateToCreateAccount(navOptions: NavOptions? = null){
    this.navigate(createAccountRoute)
}

fun NavGraphBuilder.createAccount(){
    composable(route = createAccountRoute){
        Signup(onNavigateUp = {})
    }
}