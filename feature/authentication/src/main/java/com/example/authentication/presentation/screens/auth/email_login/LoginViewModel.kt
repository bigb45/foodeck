package com.example.authentication.presentation.screens.auth.email_login

import androidx.lifecycle.ViewModel
import com.example.authentication.domain.use_cases.ValidateEmailUseCase
import com.example.authentication.domain.use_cases.ValidatePasswordUseCase
import com.example.authentication.presentation.screens.auth.data.AuthState
import com.example.authentication.presentation.screens.auth.data.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailUseCase: ValidateEmailUseCase,
    private val passwordUseCase: ValidatePasswordUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
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

            AuthEvent.Submit -> TODO()
            else -> {
//                do nothing
            }
        }
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