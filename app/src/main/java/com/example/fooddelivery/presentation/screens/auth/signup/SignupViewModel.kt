package com.example.fooddelivery.presentation.screens.auth.signup

import androidx.lifecycle.ViewModel
import com.example.fooddelivery.domain.use_cases.ValidateEmailUseCase
import com.example.fooddelivery.domain.use_cases.ValidatePasswordUseCase
import com.example.fooddelivery.domain.use_cases.ValidatePhoneNumberUseCase
import com.example.fooddelivery.domain.use_cases.ValidateUsernameUseCase
import com.example.fooddelivery.presentation.screens.auth.AuthUiState
import com.example.fooddelivery.util.AuthEvent
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
): ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())

    val signupUiState = _uiState.asStateFlow()

    fun notifyChange(event: AuthEvent){
        when(event){
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
            is AuthEvent.PhoneNumberChanged -> {
                _uiState.value = _uiState.value.copy(
                    phoneNumber = event.newPhoneNumber
                )
                validatePhoneNumber()
            }
            is AuthEvent.UsernameChanged -> {
                _uiState.value = _uiState.value.copy(
                    username = event.newUsername
                )
                validateUsername()
            }
            AuthEvent.Submit -> TODO()
        }
    }

    private fun validateEmail(): Boolean{
        val result = emailUseCase(_uiState.value.email)
        _uiState.value = _uiState.value.copy(
            emailError = result
        )
        return result.isError
    }

    private fun validatePassword(): Boolean{
        val result = passwordUseCase(_uiState.value.password)
        _uiState.value = _uiState.value.copy(
            passwordError = result
        )
        return result.isError
    }

    private fun validatePhoneNumber(): Boolean{
        val result = phoneNumberUseCase(_uiState.value.phoneNumber)
        _uiState.value = _uiState.value.copy(
            phoneNumberError = result
        )
        return result.isError
    }

    private fun validateUsername(): Boolean{
        val result = usernameUseCase(_uiState.value.username)
        _uiState.value = _uiState.value.copy(
            usernameError = result
        )
        return result.isError
    }



}