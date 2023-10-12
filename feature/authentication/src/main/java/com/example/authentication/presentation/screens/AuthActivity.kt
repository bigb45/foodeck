package com.example.authentication.presentation.screens.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authentication.presentation.nav.Navigation
import com.example.authentication.presentation.screens.auth.email_login.LoginViewModel
import com.example.authentication.presentation.screens.auth.facebook_login.FacebookLoginViewModel
import com.example.authentication.presentation.screens.auth.google_login.GoogleAuthUiClient
import com.example.authentication.presentation.screens.auth.google_login.GoogleSigninViewModel
import com.example.authentication.presentation.screens.auth.signup.SignupViewModel
import com.example.core.ui.theme.FoodDeliveryTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private lateinit var oneTapClient: SignInClient
private lateinit var signInRequest: BeginSignInRequest

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val signupViewModel: SignupViewModel by viewModels()
        val loginViewModel: LoginViewModel by viewModels()
        val facebookLoginViewModel: FacebookLoginViewModel by viewModels()
        setContent {
            FoodDeliveryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val googleSignInViewModel = viewModel<GoogleSigninViewModel>()
                    val state by googleSignInViewModel.state.collectAsState()

                    LaunchedEffect(key1 = Unit){
                        if(googleAuthUiClient.getSignedInUser() != null){
//                          TODO:  navigate to main app screen
                        }
//                       TODO:  check if signed in by facebook
                    }

                    LaunchedEffect(key1 = state.isSignInSuccessful){
//                          TODO:  navigate to main app screen
                    }

                    val launcher =
                        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val signInResult = googleAuthUiClient.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                        googleSignInViewModel.onSignInResult(signInResult)
                                    }
                                }
                            })

                    Navigation(
                        signupViewModel = signupViewModel,
                        loginViewModel = loginViewModel,
                        facebookLoginViewModel = facebookLoginViewModel,
                        state = state,
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthUiClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                    ).build()
                                )

                            }
                        },
                    )
                }
            }
        }
    }
}


