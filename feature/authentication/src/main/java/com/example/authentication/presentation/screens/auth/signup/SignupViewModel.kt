package com.example.authentication.presentation.screens.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.authentication.domain.use_cases.ValidateEmailUseCase
import com.example.authentication.domain.use_cases.ValidatePasswordUseCase
import com.example.authentication.domain.use_cases.ValidatePhoneNumberUseCase
import com.example.authentication.domain.use_cases.ValidateUsernameUseCase
import com.example.authentication.presentation.screens.auth.AuthUiState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val emailUseCase: ValidateEmailUseCase,
    private val phoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val passwordUseCase: ValidatePasswordUseCase,
    private val usernameUseCase: ValidateUsernameUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())

    val signupUiState = _uiState.asStateFlow()

    fun notifyChange(event: com.example.authentication.util.AuthEvent) {
        when (event) {
            is com.example.authentication.util.AuthEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = event.newEmail
                )
                validateEmail()
            }

            is com.example.authentication.util.AuthEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    password = event.newPassword
                )
                validatePassword()
            }

            is com.example.authentication.util.AuthEvent.PhoneNumberChanged -> {
                _uiState.value = _uiState.value.copy(
                    phoneNumber = event.newPhoneNumber
                )
                validatePhoneNumber()
            }

            is com.example.authentication.util.AuthEvent.UsernameChanged -> {
                _uiState.value = _uiState.value.copy(
                    username = event.newUsername
                )
                validateUsername()
            }

            com.example.authentication.util.AuthEvent.Submit -> TODO()
        }
    }

    private fun validateEmail(): Boolean {
        val result = emailUseCase(_uiState.value.email)
        _uiState.value = _uiState.value.copy(
            emailError = result
        )
        Log.d("viewmodel error:", result.errorMessage.toString())
        return result.isError
    }

    private fun validatePassword(): Boolean {
        val result = passwordUseCase(_uiState.value.password)
        _uiState.value = _uiState.value.copy(
            passwordError = result
        )
        return result.isError
    }

    private fun validatePhoneNumber(): Boolean {
        val result = phoneNumberUseCase(_uiState.value.phoneNumber)
        _uiState.value = _uiState.value.copy(
            phoneNumberError = result
        )
        return result.isError
    }

    private fun validateUsername(): Boolean {
        val result = usernameUseCase(_uiState.value.username)
        _uiState.value = _uiState.value.copy(
            usernameError = result
        )
        return result.isError
    }


}