package com.example.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.components.PrimaryButton
import com.example.core.ui.components.SecondaryButton
import com.example.fooddeliver.home.R

@Composable
fun Welcome(onContinueClick: () -> Unit, onSignOut: () -> Unit) {
    val viewModel: WelcomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {

        when(uiState){
            is WelcomeScreenUiState.Success -> {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(120.dp)

                )
                Text(stringResource(R.string.signed_in_successfully), style = MaterialTheme.typography.titleLarge)
                Text("Signed in as ${(uiState as WelcomeScreenUiState.Success).user.name}")
                PrimaryButton(
                    text = "Continue to Foodeck", enabled = true, onClick = onContinueClick
                )
                SecondaryButton(
                    text = "Sign out", enabled = true, onClick = onSignOut
                )
            }

            is WelcomeScreenUiState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}