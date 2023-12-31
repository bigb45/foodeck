package com.example.authentication.create_account

import android.util.Log.d
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authentication.AuthResult
import com.example.compose.gray6
import com.example.compose.seed
import com.example.core.ui.components.CustomPasswordTextField
import com.example.core.ui.components.CustomTextField
import com.example.core.ui.components.PrimaryButton
import com.example.core.ui.components.SecondaryButton
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.authentication.AuthEvent
import com.example.fooddelivery.authentication.R


@Composable
internal fun SignupRoute(
    onNavigateUp: () -> Unit,
    onLoginInsteadClick: () -> Unit,
    onAuthenticationSuccess: (String) -> Unit
) {
    val viewModel: SignupViewModel = hiltViewModel()
    val scrollState = rememberScrollState()
    val uiState by viewModel.signUpScreenUiState.collectAsState()
    val authResult by viewModel.authState.collectAsState()
    val snackbarHostState: SnackbarHostState = remember {SnackbarHostState()}

    LaunchedEffect(key1 = authResult) {
        when (authResult) {
            is AuthResult.Error -> {
                snackbarHostState.showSnackbar(message = (authResult as AuthResult.Error).errorMessage, withDismissAction = true)
            }

            is AuthResult.Success -> {
                onAuthenticationSuccess((authResult as AuthResult.Success).data.userId.toString())
            }
            is AuthResult.Loading -> {
//            TODO: make button disabled and show loading state
            }

          else -> {}
        }
    }
    FoodDeliveryTheme {
        SignupScreen(
            onNavigateUp = onNavigateUp,
            onSecondaryButtonClick = onLoginInsteadClick,
            scrollState = scrollState,
            uiState = uiState,
            notifyChange = viewModel::notifyChange,
            snackbarHostState = snackbarHostState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignupScreen(
    onNavigateUp: () -> Unit,
    scrollState: ScrollState,
    uiState: CreateAccountScreenState,
    notifyChange: (AuthEvent) -> Unit,
    onSecondaryButtonClick: () -> Unit,
    snackbarHostState: SnackbarHostState,

    ) {

    Scaffold(

        modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(R.string.create_an_account),
                style = MaterialTheme.typography.titleMedium
            )
        }, navigationIcon = {
            IconButton(
                onClick = onNavigateUp,
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        })
    },
        snackbarHost = {SnackbarHost(hostState = snackbarHostState)}) {
        SignupForm(
            scrollState = scrollState,
            uiState = uiState,
            notifyChange = notifyChange,
            onSecondaryButtonClick = onSecondaryButtonClick,
            modifier = Modifier.padding(it)

        )
    }

}

@Composable
internal fun SignupForm(
    scrollState: ScrollState,
    uiState: CreateAccountScreenState,
    notifyChange: (AuthEvent) -> Unit,
    onSecondaryButtonClick: () -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState, enabled = true)


    ) {
        Spacer(
            modifier = Modifier
                .height(14.dp)
                .fillMaxWidth()
                .background(gray6)
                .fillMaxWidth()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(23.dp)
        ) {
            Text(
                text = stringResource(R.string.input_your_credentials),
                style = MaterialTheme.typography.titleLarge
            )
            TextFields(uiState = uiState, notifyChange = notifyChange)
        }
        FormButtons(onPrimaryButtonClick = notifyChange, onSecondaryButtonClick = onSecondaryButtonClick)
    }
}

@Composable
fun TextFields(
    uiState: CreateAccountScreenState,
    notifyChange: (AuthEvent) -> Unit,
) {
    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {


        CustomTextField(
            value = uiState.username,
            error = uiState.usernameError,
            onValueChange = { newUsername ->
                notifyChange(AuthEvent.UsernameChanged(newUsername))
            },
            label = stringResource(R.string.username),
        )


        CustomTextField(
            value = uiState.email,
            keyboardType = KeyboardType.Email,
            error = uiState.emailError,
            onValueChange = { newEmail ->
                notifyChange(AuthEvent.EmailChanged(newEmail))
            },
            label = stringResource(R.string.email),
        )

        CustomTextField(
            value = uiState.phoneNumber,

            keyboardType = KeyboardType.Phone,
            onValueChange = { newPhoneNumber ->
                notifyChange(AuthEvent.PhoneNumberChanged(newPhoneNumber))
            },
            error = uiState.phoneNumberError,
            label = stringResource(R.string.phone_number)
        )

        CustomPasswordTextField(
            value = uiState.password,
            keyboardType = KeyboardType.Password,
            onValueChange = { newPassword ->
                notifyChange(AuthEvent.PasswordChanged(newPassword))
            },

            label = stringResource(R.string.password),
            error = uiState.passwordError,
        )

    }
}

@Composable
internal fun FormButtons(onPrimaryButtonClick: (AuthEvent) -> Unit, onSecondaryButtonClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(23.dp)
    ) {
        PrimaryButton(
            text = stringResource(R.string.create_an_account), enabled = true, onClick = {
                onPrimaryButtonClick(AuthEvent.Submit)
            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                containerColor = seed
            )
        )
        SecondaryButton(
            onClick = onSecondaryButtonClick,
            text = stringResource(R.string.login_instead),
            enabled = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}