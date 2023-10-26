package com.example.facebook

import android.util.Log
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.components.PrimaryButton
import com.example.data.models.AuthResult
import com.example.fooddelivery.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult


@Composable
internal fun FacebookLogin(
    onContinueClick: () -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: FacebookLoginViewModel = hiltViewModel()

    val callbackManager = CallbackManager.Factory.create()
    val loginManager = LoginManager.getInstance()

    val state by viewModel.authResult.collectAsState()

//  open webpage to log user in
    LaunchedEffect(key1 = Unit) {
        viewModel.setStateToLoading()
        loginManager.logIn(
            context as ActivityResultRegistryOwner, callbackManager, listOf("email")
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
    FacebookLoginScreen(
        onNavigationIconClick = onNavigationIconClick,
        state = state,
        onContinueClick = onContinueClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FacebookLoginScreen(
    onNavigationIconClick: () -> Unit,
    onContinueClick: () -> Unit,
    state: AuthResult,

    ) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Sign in") }, navigationIcon = {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            })
        },
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Log.d("state", state.toString())
            when (state) {
                AuthResult.Loading -> CircularProgressIndicator()
                AuthResult.Cancelled -> FacebookLoginError(errorMessage = "Cancelled by user")
                is AuthResult.Error -> FacebookLoginError(errorMessage = state.errorMessage)
                is AuthResult.Success -> FacebookLoginSuccess(
                    username = state.data.username?: "Unknown", onContinueClick = onContinueClick
                )

                else -> {}
            }
        }
    }
}

@Composable
internal fun FacebookLoginSuccess(username: String, onContinueClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {

        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(120.dp)

        )
        // TODO: move text into resource string file
        Text("Signed in successfully", style = MaterialTheme.typography.titleLarge)
        Text("Signed in as $username")
        PrimaryButton(
            text = stringResource(
                R.string.continue_to_foodeck
            ), enabled = true, onClick = onContinueClick
        )
//        SecondaryButton(
//            text = "Sign out", enabled = true, onClick = {  }
//        )
    }
}

@Composable
internal fun FacebookLoginError(errorMessage: String) {
    Icon(
        imageVector = Icons.Filled.Error,
        contentDescription = null,
        tint = Color.Red,
        modifier = Modifier.size(120.dp)
    )
    Text(
        errorMessage, style = MaterialTheme.typography.titleLarge
    )
}