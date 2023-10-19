package com.example.authentication.presentation.screens.auth.email_login

import androidx.lifecycle.ViewModel
import com.example.data.models.AuthState
import com.example.authentication.presentation.screens.auth.data.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailUseCase: com.example.domain.use_cases.ValidateEmailUseCase,
    private val passwordUseCase: com.example.domain.use_cases.ValidatePasswordUseCase,
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