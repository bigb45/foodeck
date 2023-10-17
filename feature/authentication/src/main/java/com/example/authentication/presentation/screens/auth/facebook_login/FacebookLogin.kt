package com.example.authentication.presentation.screens.auth.facebook_login

import androidx.activity.result.ActivityResultRegistryOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.authentication.presentation.screens.auth.SignInResult
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult


@Composable
fun FacebookLogin(navController: NavController) {
    val context = LocalContext.current
    val viewModel: FacebookLoginViewModel = hiltViewModel()

    val callbackManager = CallbackManager.Factory.create()
    val loginManager = LoginManager.getInstance()

    val state by viewModel.authResult.collectAsState()

//  open webpage to log user in
    LaunchedEffect(key1 = Unit) {
        viewModel.setStateToLoading()
        loginManager.logIn(
            context as ActivityResultRegistryOwner,
            callbackManager,
            listOf("email")
        )
    }

    loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
        override fun onCancel() {
            viewModel.cancelLogin()
        }

        override fun onError(error: FacebookException) {
            viewModel.loginError(exception = error)
        }

        override fun onSuccess(result: LoginResult) {
            viewModel.handleLogInSuccess(result)
        }
    })

    SignInResult(navController = navController)
}

