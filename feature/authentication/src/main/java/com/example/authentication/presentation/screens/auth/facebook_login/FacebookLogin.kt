package com.example.authentication.presentation.screens.auth.facebook_login

import androidx.activity.result.ActivityResultRegistryOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacebookLogin(viewModel: FacebookLoginViewModel, navController: NavController?) {
    val context = LocalContext.current

    val callbackManager = CallbackManager.Factory.create()
    val loginManager = LoginManager.getInstance()

    val state by viewModel.loginState.collectAsState()
    val userData by viewModel.userData.collectAsState()
    var requestStarted = false
//  open webpage to log user in
    LaunchedEffect(key1 = Unit) {
        loginManager.logIn(
            context as ActivityResultRegistryOwner,
            callbackManager,
            listOf("email")
        )
        requestStarted = true
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
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Login with Facebook") }, navigationIcon = {
                IconButton(onClick = { navController?.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            })
        },
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (state.isSignInSuccessful) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle, contentDescription = null,
                    tint = colorScheme.primary,
                    modifier = Modifier
                        .size(120.dp)

                )
                // TODO: move text into resource string file
                Text("Signed in successfully", style = typography.titleLarge)
                // TODO: handle this case 
                Text("Signed in as ${userData.data?.username ?: "Unknown"}")
            } else if (requestStarted) {
                Icon(
                    imageVector = Icons.Filled.Error, contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .size(120.dp)

                )
                Text(
                    state.signInError ?: "Unknown error.", style = typography.titleLarge
                )
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

