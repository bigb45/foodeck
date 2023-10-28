package com.example.authentication.presentation.screens.auth.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.ui.components.Hyperlink
import com.example.core.ui.components.PrimaryButton
import com.example.data.models.AuthResult
import com.example.authentication.presentation.screens.auth.google_login.GoogleSignInViewModel
import com.example.compose.facebook_color
import com.example.compose.google_color
import com.example.compose.seed
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.fooddelivery.R
import com.example.core.ui.components.SecondaryButton
import com.example.create_account.navigation.navigateToCreateAccount
import com.example.email.navigation.navigateToEmail
import com.example.facebook.navigation.navigateToFacebook
import com.example.fooddelivery.R.string.create_an_account
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch



@Composable
fun LoginMethods(
    navController: NavController,
    onGoogleAuthenticationSuccess: (String) -> Unit
) {
    val context = LocalContext.current


    val viewModel: GoogleSignInViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

//    Initiate Google sign in
    val scope = rememberCoroutineScope()
    val googleAuthUiClient by lazy {
        com.example.data.util.GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
//    TODO: move this to the main app module
    LaunchedEffect(key1 = state) {
        when (state) {
            AuthResult.Cancelled -> {}
            is AuthResult.Error -> {
// TODO: show snackbar
                Log.d("google", "error signing in with google")

            }

            is AuthResult.Success -> {
                onGoogleAuthenticationSuccess((state as AuthResult.Success).data.userId.toString())

            }

            AuthResult.Loading -> {}

            else -> {}
        }
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                if (result.resultCode == ComponentActivity.RESULT_OK) {
                    scope.launch {
                        val signInResult = googleAuthUiClient.signInWithIntent(
                            intent = result.data ?: return@launch
                        )
                        viewModel.onSignInResult(signInResult)
                    }
                }
            })

    FoodDeliveryTheme {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()

        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.loginheader),
                    contentDescription = null,
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Row(
                    Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painterResource(id = R.drawable.foodeck), contentDescription = null)
                    Text(
                        text = "Foodeck",
                        style = TextStyle(
                            fontSize = 34.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                        ),

                        )
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                    .verticalScroll(rememberScrollState(), true)
            ) {

                PrimaryButton(
                    text = "Login with Google", onClick = {
                        scope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    },
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = google_color
                    ),
                )

                PrimaryButton(
                    text = stringResource(R.string.login_with_facebook),
                    onClick = {
                        navController.navigateToFacebook()
                    },
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = facebook_color
                    ),
                )
                PrimaryButton(
                    text = stringResource(R.string.login_via_email),
                    onClick = {
                        navController.navigateToEmail()
                    },
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = seed
                    ),
                )


                SecondaryButton(text = stringResource(create_an_account), enabled = true, onClick = {
                    navController.navigateToCreateAccount()
                })

                Hyperlink(
                    text = stringResource(R.string.terms_and_conditions),
                    hyperLinkText = stringResource(R.string.terms_conditions_hyper_text),
                    )
            }

        }
    }
}
