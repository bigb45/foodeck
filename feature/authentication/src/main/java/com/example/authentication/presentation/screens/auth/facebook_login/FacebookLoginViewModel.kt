package com.example.authentication.presentation.screens.auth.facebook_login

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.authentication.presentation.screens.auth.data.AuthResult
import com.example.authentication.presentation.screens.auth.data.UserData
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FacebookLoginViewModel @Inject constructor() : ViewModel() {
    private val _authResult = MutableStateFlow<AuthResult>(AuthResult.Loading)

    val authResult: StateFlow<AuthResult> = _authResult


    fun handleLogInSuccess(result: LoginResult) {
        val graphRequest = GraphRequest.newMeRequest(
            result.accessToken
        ) { user, _ ->
            if (user != null) {
                val userId = user.getString("id")
                val username = user.getString("name")

                val profilePictureUrl = "https://graph.facebook.com/$userId/picture?type=large"

                _authResult.value = AuthResult.Success(
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
        _authResult.value = AuthResult.Cancelled
    }

    fun loginError(exception: FacebookException) {
        _authResult.value = AuthResult.Error(errorMessage = exception.message?: "Unknown error.")
    }

    fun setStateToLoading() {
        _authResult.value = AuthResult.Loading
    }
}