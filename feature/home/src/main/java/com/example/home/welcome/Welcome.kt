package com.example.home.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.components.PrimaryButton
import com.example.core.ui.components.SecondaryButton

@Composable
fun Welcome(userId: String) {
    val viewModel: WelcomeViewModel = hiltViewModel()
    viewModel.getUserFromId(userId)
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
        Text("Signed in as username")
        PrimaryButton(
            text = "Continue to Foodeck", enabled = true, onClick = {}
        )
        SecondaryButton(
            text = "Sign out", enabled = true, onClick = { }
        )
    }
}