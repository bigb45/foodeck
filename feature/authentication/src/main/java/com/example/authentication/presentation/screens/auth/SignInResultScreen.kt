package com.example.authentication.presentation.screens.auth

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.authentication.presentation.components.PrimaryButton
import com.example.fooddelivery.presentation.components.SecondaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInResult(
    navController: NavController,
    state: AuthResult
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Sign in") }, navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
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
            when (state) {
                AuthResult.Cancelled -> ErrorState(errorMessage = "Cancelled by user")
                is AuthResult.Error -> ErrorState(errorMessage = state.errorMessage)
                AuthResult.Loading -> CircularProgressIndicator()
                is AuthResult.Success -> SuccessState(
                    username = state.data.username,
                    onClick = {})
            }
        }
    }
}

@Composable
fun SuccessState(username: String, onClick: () -> Unit, ) {
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
            text = "Continue to Foodeck", enabled = true, onClick = onClick
        )

    }
}

@Composable
fun ErrorState(errorMessage: String) {
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