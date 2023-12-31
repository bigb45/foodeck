package com.example.authentication.facebook_login

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.AuthResult
import com.example.common.Result
import com.example.common.asResult
import com.example.data.models.SignInAuthResponseModel
import com.example.data.models.TokenAuthResponseModel
import com.example.data.models.UserDetailsModel
import com.example.domain.use_cases.AddUserInformationUseCase
import com.example.domain.use_cases.AuthenticateUserWithTokenUseCase
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectIndexed
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
            authenticateWithToken(token=result.accessToken.token, provider="facebook").asResult().collect{ result ->
                when(result){
                    is Result.Error -> {
                        _authResult.value = AuthResult.Error(result.exception?.message ?: "An unknown occurred")
                    }
                    Result.Loading -> {
                        _authResult.value = AuthResult.Loading

                    }
                    is Result.Success -> {
                        _authResult.value = AuthResult.Success(UserDetailsModel(userId = (result.data as TokenAuthResponseModel.SignInSuccess).tokens.userId))

                    }
                }
            }


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