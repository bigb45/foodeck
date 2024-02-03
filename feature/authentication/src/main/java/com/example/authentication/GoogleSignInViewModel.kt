package com.example.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.common.asResult
import com.example.data.models.InternalServerException
import com.example.data.models.TokenAuthResponseModel
import com.example.data.models.UnknownException
import com.example.data.models.UserDetails
import com.example.domain.use_cases.AuthenticateUserWithTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleSignInViewModel @Inject constructor(
    private val authenticateWithToken: AuthenticateUserWithTokenUseCase,
) : ViewModel() {
    private val _authResult = MutableStateFlow<AuthResult>(AuthResult.SignedOut)
    private val _uiState = MutableStateFlow<LoginMethodsScreenState>(LoginMethodsScreenState.SignedOut)

    val authResult: StateFlow<AuthResult> = _authResult
    val uiState: StateFlow<LoginMethodsScreenState> = _uiState

    fun onSignInResult(result: String) {
        viewModelScope.launch {
            authenticateWithToken(token=result, provider="google").asResult().collect{
                result ->
                when (result) {
                    is Result.Error -> {
                        _authResult.value = AuthResult.Error(result.exception?.message ?: "Unknown error")
                        Log.d("error", "${result.exception?.message}")
                        handleAuthError(result)
                    }
                    Result.Loading -> {
                        _authResult.value = AuthResult.Loading
                        Log.d("error", "loading")
                    }
                    is Result.Success -> {
                        _authResult.value = AuthResult.Success(UserDetails(userId = (result.data as TokenAuthResponseModel.SignInSuccess).tokens.userId))
                        Log.d(
                            "error",
                            "user data ${(result.data as TokenAuthResponseModel.SignInSuccess).tokens.userId}"
                        )
                    }
                }
            }
        }
    }

    private fun handleAuthError(result: Result.Error) {
        when (result.exception) {
            is InternalServerException -> {
                _uiState.value = LoginMethodsScreenState.Error("An internal server exception occurred")
            }

            is UnknownException -> {
                _uiState.value = LoginMethodsScreenState.Error("An unknown error occurred")

            }

        }

    }

}