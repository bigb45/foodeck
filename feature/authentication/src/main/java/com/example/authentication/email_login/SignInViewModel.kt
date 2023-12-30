package com.example.authentication.email_login

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.AuthResult
import com.example.common.Result
import com.example.data.models.FieldError
import com.example.data.models.UserSignInModel
import com.example.authentication.AuthEvent
import com.example.common.asResult
import com.example.data.util.TextFieldMessages
import com.example.domain.use_cases.SignUserInUseCase
import com.example.domain.use_cases.ValidateEmailUseCase
import com.example.domain.use_cases.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.data.models.InvalidCredentialsException
import com.example.data.models.SignInAuthResponseModel
import com.example.data.models.UserDetailsModel
import com.example.data.models.UserNotFoundException

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val emailUseCase: ValidateEmailUseCase,
    private val passwordUseCase: ValidatePasswordUseCase,
    private val signUserInUseCase: SignUserInUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInScreenState())
    private var _authResult: MutableStateFlow<AuthResult> = MutableStateFlow(AuthResult.SignedOut)
    val authResult: StateFlow<AuthResult> = _authResult
    val signInUiState = _uiState

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
                        UserSignInModel(
                            email = email, password = password
                        )
                    }
                    signIn(userInfo)

                }
            }

            else -> {}
        }
    }

    private fun signIn(user: UserSignInModel) {
//        TODO: use stateIn
        viewModelScope.launch {
            signUserInUseCase(user).asResult().collect { result ->
//                    LoginAuthResponseModel.InternalServerError -> d("error", "server is down")
//                    LoginAuthResponseModel.InvalidCredentials -> d("error", "wrong password")
//                    LoginAuthResponseModel.Loading -> {
//                        _authResult.value = AuthResult.Loading
//                    }
//                    is LoginAuthResponseModel.LoginFailure -> d("error", "user not found")
//                    is LoginAuthResponseModel.LoginSuccess -> {
//                        preferencesManager.writeToken(R.string.access_token, result.tokens.accessToken)
////                        tokenRepository.getAccessToken()
//                        _authResult.value = AuthResult.Success(UserData(userId = result.tokens.userId))
//                    }
//                    LoginAuthResponseModel.UnknownError -> d("error", "unknown error")
//                    LoginAuthResponseModel.UserNotFound -> {
//                        d("error", "user not found")
//                    }
                when (result) {
                    is Result.Error -> {
                        _authResult.value = AuthResult.Error(result.exception?.message ?: "Unknown error")
                        d("error", "${result.exception?.message}")
                        handleAuthError(result)
                    }
                    Result.Loading -> {
                        _authResult.value = AuthResult.Loading
                        d("error", "loading")
                    }
                    is Result.Success -> {
                        _authResult.value = AuthResult.Success(UserDetailsModel(userId = (result.data as SignInAuthResponseModel.SignInSuccess).tokens.userId))
                        d("error", "user data ${(result.data as SignInAuthResponseModel.SignInSuccess).tokens.userId}")
                    }
                }

            }
        }
    }

    private fun handleAuthError(result: Result.Error) {
        when (result.exception) {
            is UserNotFoundException -> {
                _uiState.value = _uiState.value.copy(
                    emailError = FieldError(
                        isError = true,
                        TextFieldMessages.UNREGISTERED_USER
                    )
                )
            }

            is InvalidCredentialsException -> {
                _uiState.value = _uiState.value.copy(
                    passwordError = FieldError(
                        isError = true,
                        TextFieldMessages.INVALID_CREDENTIALS
                    )
                )
            }

//            is InternalServerException -> {
//                _uiState.value = _uiState.value.copy(
//                    snackbarError = SnackbarError("Internal server error occurred")
//                )
//            }


        }

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