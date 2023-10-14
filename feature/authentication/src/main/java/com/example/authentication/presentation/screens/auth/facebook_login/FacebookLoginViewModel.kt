package com.example.authentication.presentation.screens.auth.facebook_login

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.authentication.presentation.screens.auth.AuthResult
import com.example.authentication.presentation.screens.auth.UserData
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FacebookLoginViewModel : ViewModel() {
    private val _signInState = MutableStateFlow<AuthResult>(AuthResult.Loading)

    val signInState: StateFlow<AuthResult> = _signInState


    fun handleLogInSuccess(result: LoginResult) {
        val graphRequest = GraphRequest.newMeRequest(
            result.accessToken
        ) { user, _ ->
            if (user != null) {
                val userId = user.getString("id")
                val username = user.getString("name")

                val profilePictureUrl = "https://graph.facebook.com/$userId/picture?type=large"

                _signInState.value = AuthResult.Success(
                    data = UserData(
                        userId = userId,
                        username = username,
                        profilePictureUrl = profilePictureUrl
                    )
                )
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()

    }

    fun cancelLogin() {
        _signInState.value = AuthResult.Cancelled
    }

    fun loginError(exception: FacebookException) {
        _signInState.value = AuthResult.Error(errorMessage = exception.message?: "Unknown error.")
    }

    fun setStateToLoading() {
        _signInState.value = AuthResult.Loading
    }
}