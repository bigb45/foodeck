package com.example.authentication.presentation.screens.auth.facebook_login

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.authentication.presentation.screens.auth.SignInResult
import com.example.authentication.presentation.screens.auth.SignInState
import com.example.authentication.presentation.screens.auth.UserData
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FacebookLoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(SignInState())
    val loginState = _loginState.asStateFlow()
    private val _userData = MutableStateFlow(SignInResult(null, null))
    val userData = _userData.asStateFlow()
    fun handleLogInSuccess(result: LoginResult) {
        val graphRequest = GraphRequest.newMeRequest(
            result.accessToken
        ) { user, _ ->
            if (user != null) {
                val userId = user.getString("id")
                val username = user.getString("name")

                val profilePictureUrl = "https://graph.facebook.com/$userId/picture?type=large"
                _userData.update {
                    SignInResult(
                        data = UserData(
                            userId = userId,
                            username = username,
                            profilePictureUrl = profilePictureUrl
                        ),
                        errorMessage = null
                    )

                }
                _loginState.update {
                    it.copy(
                        isSignInSuccessful = true,
                        signInError = null

                    )
                }
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()

    }

    fun cancelLogin() {
        _loginState.update {
            it.copy(
                isSignInSuccessful = false,
                signInError = "Cancelled by user"
            )
        }
    }

    fun loginError(exception: FacebookException) {
        _loginState.update {
            it.copy(
                isSignInSuccessful = false,
                signInError = exception.message
            )
        }
    }
}