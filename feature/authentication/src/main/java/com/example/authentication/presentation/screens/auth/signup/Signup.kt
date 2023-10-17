package com.example.authentication.presentation.screens.auth.signup

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.authentication.presentation.components.CustomPasswordTextField
import com.example.authentication.presentation.components.CustomTextField
import com.example.authentication.presentation.components.PrimaryButton
import com.example.authentication.presentation.screens.auth.data.AuthEvent
import com.example.compose.gray6
import com.example.compose.seed
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.fooddelivery.presentation.components.SecondaryButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signup(navController: NavController) {
    val viewModel: SignupViewModel = hiltViewModel()
    val scrollState = rememberScrollState()
    val uiState by viewModel.signupUiState.collectAsState()
    val authResult by viewModel.authState.collectAsState()

    FoodDeliveryTheme {

        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = { Text("Create an Account", style = typography.titleMedium) },
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
                        modifier = Modifier.fillMaxWidth()
                    ) {


                        CustomTextField(
                            value = uiState.username,
                            error = uiState.usernameError,
                            onValueChange = { newUsername ->
                                viewModel.notifyChange(AuthEvent.UsernameChanged(newUsername))
                            },
                            label = "Username",
                        )


                        CustomTextField(
                            value = uiState.email,
                            keyboardType = KeyboardType.Email,
                            error = uiState.emailError,
                            onValueChange = { newEmail ->
                                viewModel.notifyChange(AuthEvent.EmailChanged(newEmail))
                            },
                            label = "Email",
                        )

                        CustomTextField(
                            value = uiState.phoneNumber,

                            keyboardType = KeyboardType.Phone,
                            onValueChange = { newPhoneNumber ->
                                viewModel.notifyChange(AuthEvent.PhoneNumberChanged(newPhoneNumber))
                            },
                            error = uiState.phoneNumberError,
                            label = "Phone number"
                        )

                        CustomPasswordTextField(
                            value = uiState.password,
                            keyboardType = KeyboardType.Password,
                            onValueChange = { newPassword ->
                                viewModel.notifyChange(AuthEvent.PasswordChanged(newPassword))
                            },

                            label = "Password",
                            error = uiState.passwordError,
//                            data = TextFieldData(uiState.password, uiState.passwordError)
                        )

                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(23.dp)
                ) {
//                    val isFormValid by viewModel.isFormValid.collectAsState()
                    PrimaryButton(
                        text = "Create an Account",
                        enabled = true,
                        onClick = {
                            viewModel.notifyChange(AuthEvent.Submit)

                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = seed
                        )
                    )
                    SecondaryButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        text = "Login instead",
                        enabled = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

