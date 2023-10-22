package com.example.authentication.presentation.screens.auth.email_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.presentation.screens.auth.data.AuthEvent
import com.example.data.models.AuthResult
import com.example.data.models.AuthState
import com.example.data.models.FieldError
import com.example.data.models.UserLoginCredentials
import com.example.data.util.ValidationResult
import com.example.domain.use_cases.SignUserInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailUseCase: com.example.domain.use_cases.ValidateEmailUseCase,
    private val passwordUseCase: com.example.domain.use_cases.ValidatePasswordUseCase,
    private val signUserInUseCase: SignUserInUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
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
                    val userInfo = with(_uiState.value) {
                        UserLoginCredentials(
                            email = email,
                            password = password
                        )
                    }
                    signIn(userInfo)

                }
            }

            else -> {}
        }
    }

    private fun signIn(user: UserLoginCredentials) {
        viewModelScope.launch {
            _authResult.value = signUserInUseCase(user)
            when (val result = _authResult.value) {
                is AuthResult.Error -> {
                    handleAuthError(result)
                }

                else -> {}
            }

        }
    }

    private fun handleAuthError(result: AuthResult.Error) {

        _uiState.value = _uiState.value.copy(
            emailError = FieldError(
                isError = true,
                ValidationResult.INVALID_CREDENTIALS

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