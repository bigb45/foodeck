package com.example.email

import android.widget.Toast
import androidx.compose.foundation.ScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.gray6
import com.example.compose.seed
import com.example.core.ui.components.CustomPasswordTextField
import com.example.core.ui.components.CustomTextField
import com.example.core.ui.components.Hyperlink
import com.example.core.ui.components.PrimaryButton
import com.example.core.ui.components.SecondaryButton
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.data.models.AuthEvent
import com.example.data.models.AuthResult
import com.example.data.models.AuthState
import com.example.fooddelivery.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EmailLoginRoute(
    onNavigateUpClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
    onLoginSuccess: () -> Unit,

) {
    val viewModel: LoginViewModel = hiltViewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val uiState by viewModel.loginUiState.collectAsState()
    val authResult by viewModel.authResult.collectAsState()
    LaunchedEffect(key1 = authResult) {
        when (authResult) {
            is AuthResult.Error -> {
                Toast.makeText(
                    context, (authResult as AuthResult.Error).errorMessage, Toast.LENGTH_SHORT
                ).show()
            }

            is AuthResult.Success -> {
                Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                onLoginSuccess()
            }

            AuthResult.Loading -> {}
            AuthResult.SignedOut -> {}
            AuthResult.Cancelled -> {}
        }
    }
    FoodDeliveryTheme {

        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {
                Text(
                    "Create an Account", style = typography.titleMedium
                )
            }, navigationIcon = {
                IconButton(
                    onClick = onNavigateUpClick,
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            })
        }) {
            LoginForm(
                modifier = Modifier.padding(it),
                scrollState = scrollState,
                notifyChange = viewModel::notifyChange,
                uiState = uiState,
                onSecondaryButtonClick = onSecondaryButtonClick
            )
        }
    }
}

@Composable
internal fun LoginForm(
    modifier: Modifier,
    scrollState: ScrollState,
    onSecondaryButtonClick: () -> Unit,
    notifyChange: (AuthEvent) -> Unit,
    uiState: AuthState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState, enabled = true)
    ) {
        Spacer(
            modifier = modifier
                .height(14.dp)
                .fillMaxWidth()
                .background(gray6)
                .fillMaxWidth()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(23.dp)


        ) {
            Text(text = "Input your credentials", style = typography.titleLarge)

            TextFields(notifyChange = notifyChange, uiState = uiState)
            FormButtons(
                onPrimaryButtonClick = notifyChange,
                onSecondaryButtonClick = onSecondaryButtonClick
            )
        }

    }
}

@Composable
internal fun TextFields(notifyChange: (AuthEvent) -> Unit, uiState: AuthState) {
    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        CustomTextField(
            value = uiState.email,
            keyboardType = KeyboardType.Email,
            onValueChange = { newEmail ->
                notifyChange(AuthEvent.EmailChanged(newEmail))
            },
            label = "Email",
            error = uiState.emailError,

            )

        CustomPasswordTextField(
            value = uiState.password,
            keyboardType = KeyboardType.Password,

            onValueChange = { newPassword ->
                notifyChange(AuthEvent.PasswordChanged(newPassword))
            },

            label = "Password",
            error = uiState.passwordError,

            )


    }
    Hyperlink(
        text = "Forgot Password?", url = "example.com", hyperLinkText = "Forgot Password?"
    )
}

@Composable
internal fun FormButtons(
    onPrimaryButtonClick: (AuthEvent) -> Unit,
    onSecondaryButtonClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PrimaryButton(
            text = stringResource(R.string.login), enabled = true, onClick = {
                onPrimaryButtonClick(AuthEvent.Submit)
            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                containerColor = seed
            )
        )
        SecondaryButton(
            onClick = onSecondaryButtonClick,
            text = stringResource(R.string.create_an_account_instead),
            enabled = true,
//                        modifier = Modifier.fillMaxWidth()
        )
    }
}