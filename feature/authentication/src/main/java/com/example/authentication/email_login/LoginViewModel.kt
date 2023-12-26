package com.example.authentication.email_login

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.AuthResult
import com.example.common.Result
import com.example.data.data.FieldError
import com.example.data.models.UserLoginCredentials
import com.example.authentication.AuthEvent
import com.example.authentication.create_account.CreateAccountScreenState
import com.example.data.models.LoginAuthResponseModel
import com.example.data.util.AccessTokenRemoteDataSource
import com.example.data.util.PreferencesManager
import com.example.data.util.ValidationResult
import com.example.domain.use_cases.SignUserInUseCase
import com.example.domain.use_cases.ValidateEmailUseCase
import com.example.domain.use_cases.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.data.R
import com.example.data.models.UserData

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailUseCase: ValidateEmailUseCase,
    private val passwordUseCase: ValidatePasswordUseCase,
    private val signUserInUseCase: SignUserInUseCase,
    private val tokenRepository: AccessTokenRemoteDataSource,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountScreenState())
    private var _authResult: MutableStateFlow<AuthResult> = MutableStateFlow(AuthResult.SignedOut)
    val authResult: StateFlow<AuthResult> = _authResult
    val loginUiState = _uiState

    fun notifyChange(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = event.newEmail
                )
                validateEmail()
            }

            is AuthEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    password = event.newPassword
                )
                validatePassword()
            }

            AuthEvent.Submit -> {
                if (validateFields()) {
                    _authResult.value = AuthResult.Loading
                    val userInfo = with(_uiState.value) {
                        UserLoginCredentials(
                            email = email, password = password
                        )
                    }
                    signIn(userInfo)

                }
            }

            else -> {}
        }
    }

    private fun signIn(user: UserLoginCredentials) {
//        TODO: use stateIn
        viewModelScope.launch {
            signUserInUseCase(user).collect { result ->

                when (result) {
                    LoginAuthResponseModel.InternalServerError -> d("error", "server is down")
                    LoginAuthResponseModel.InvalidCredentials -> d("error", "wrong password")
                    LoginAuthResponseModel.Loading -> {
                        _authResult.value = AuthResult.Loading
                    }
                    is LoginAuthResponseModel.LoginFailure -> d("error", "user not found")
                    is LoginAuthResponseModel.LoginSuccess -> {
                        preferencesManager.writeToken(R.string.access_token, result.tokens.accessToken)
//                        tokenRepository.getAccessToken()
                        _authResult.value = AuthResult.Success(UserData(userId = result.tokens.userId))
                    }
                    LoginAuthResponseModel.UnknownError -> d("error", "unknown error")
                    LoginAuthResponseModel.UserNotFound -> {
                        d("error", "user not found")
                    }
                }

            }
        }
    }

    private fun handleAuthError(result: Result.Error) {
        _uiState.value = _uiState.value.copy(
            emailError = FieldError(
                isError = true, ValidationResult.INVALID_CREDENTIALS

            )
        )

    }

    private fun validateFields(): Boolean {
        return (validateEmail() && validatePassword())
    }

    private fun validateEmail(): Boolean {
        val result = emailUseCase(_uiState.value.email)
        _uiState.value = _uiState.value.copy(
            emailError = result
        )
        return !result.isError
    }

    private fun validatePassword(): Boolean {
        val result = passwordUseCase(_uiState.value.password)
        _uiState.value = _uiState.value.copy(
            passwordError = result
        )
        return !result.isError
    }
}