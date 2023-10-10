package com.example.authentication.presentation.screens.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.authentication.presentation.nav.AuthNavigation
import com.example.authentication.presentation.screens.auth.email_login.LoginViewModel
import com.example.authentication.presentation.screens.auth.signup.SignupViewModel
import com.example.core.ui.theme.FoodDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val signupViewModel: SignupViewModel by viewModels()
        val loginViewModel: LoginViewModel by viewModels()
        setContent {
            FoodDeliveryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AuthNavigation(
                        signupViewModel,
                        loginViewModel,
                    )
                }
            }
        }
    }
}


