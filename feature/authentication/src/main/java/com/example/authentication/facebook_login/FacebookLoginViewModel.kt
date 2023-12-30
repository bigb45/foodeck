package com.example.authentication.facebook_login

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.AuthResult
import com.example.data.models.UserDetailsModel
import com.example.domain.use_cases.AddUserInformationUseCase
import com.example.domain.use_cases.AuthenticateUserWithTokenUseCase
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacebookLoginViewModel @Inject constructor(
    private val authenticateWithToken: AuthenticateUserWithTokenUseCase,
) :
    ViewModel() {
    private val _authResult = MutableStateFlow<AuthResult>(AuthResult.Loading)
    val authResult: StateFlow<AuthResult> = _authResult

    fun handleLogInSuccess(result: LoginResult) {
        viewModelScope.launch {
            authenticateWithToken(token=result.accessToken.token, provider="facebook")
        }

    }


    fun cancelLogin() {
        _authResult.value = AuthResult.Cancelled
    }

    fun loginError(exception: FacebookException) {
        _authResult.value = AuthResult.Error(errorMessage = exception.message ?: "Unknown error.")
    }

    fun setStateToLoading() {
        _authResult.value = AuthResult.Loading
    }
}