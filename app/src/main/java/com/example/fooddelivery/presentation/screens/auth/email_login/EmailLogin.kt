package com.example.fooddelivery.presentation.screens.auth.email_login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.gray6
import com.example.fooddelivery.presentation.components.Hyperlink
import com.example.fooddelivery.presentation.components.CustomButton
import com.example.fooddelivery.presentation.components.CustomOutlinedButton
import com.example.fooddelivery.presentation.screens.auth.signup.getStringResourceFromFieldError
import com.example.fooddelivery.presentation.ui.theme.FoodDeliveryTheme
import com.example.fooddelivery.util.AuthEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailLogin(navController: NavController, viewModel: LoginViewModel) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.loginUiState.collectAsState()
    FoodDeliveryTheme {

        Scaffold(modifier = Modifier
            .fillMaxSize(), topBar = {
            TopAppBar(title = {
                Text(
                    "Create an Account",
                    style = typography.titleMedium
                )
            },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                })
        }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState, enabled = true)


            ) {
                Spacer(
                    modifier = Modifier
                        .padding(it)
                        .height(14.dp)
                        .fillMaxWidth()
                        .background(gray6)
                        .fillMaxWidth()
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(23.dp)


                ) {
                    Text(text = "Input your credentials", style = typography.titleLarge)

                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {


                        OutlinedTextField(
                            value = uiState.email,
                            supportingText = {
                                Text(getStringResourceFromFieldError(uiState.emailError))
                            },
                            isError = uiState.emailError.isError,
                            onValueChange = { newEmail ->
                                viewModel.notifyChange(AuthEvent.EmailChanged(newEmail))
                            },
                            label = { Text("Email") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()

                        )


                        var passwordVisible by remember { mutableStateOf(false) }
                        OutlinedTextField(
                            value = uiState.password,
                            isError = uiState.passwordError.isError,
                            supportingText = {
                                Text(getStringResourceFromFieldError(fieldError = uiState.passwordError))
                            },
                            onValueChange = { newPassword ->
                                viewModel.notifyChange(AuthEvent.PasswordChanged(newPassword))
                            },
                            label = { Text("Password") },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,

                            trailingIcon = {
                                val icon =
                                    if (passwordVisible && uiState.password.isNotEmpty()) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                                IconButton(onClick = {
                                    passwordVisible = !passwordVisible
                                }) {
                                    Icon(imageVector = icon, contentDescription = null)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),

                            )
                    }
                    Hyperlink(
                        text = "Forgot Password?",
                        url = "example.com",
                        hyperLinkText = "Forgot Password?"
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(23.dp)
                ) {
                    CustomButton(
                        onClick = { /*TODO validate user input and save to info. use view-model with use-cases*/ },
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Login",
                        enabled = true
                    )
                    CustomOutlinedButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        text = "Create an account instead",
                        enabled = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

