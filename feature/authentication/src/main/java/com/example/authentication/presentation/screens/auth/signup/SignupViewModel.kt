package com.example.authentication.presentation.screens.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.presentation.screens.auth.data.AuthEvent
import com.example.data.models.AuthResult
import com.example.data.models.AuthState
import com.example.data.models.ErrorCode
import com.example.data.models.FieldError
import com.example.data.models.NewUserData
import com.example.data.util.ValidationResult
import com.example.domain.use_cases.SignUserUpUseCase
import com.example.domain.use_cases.ValidateEmailUseCase
import com.example.domain.use_cases.ValidatePasswordUseCase
import com.example.domain.use_cases.ValidatePhoneNumberUseCase
import com.example.domain.use_cases.ValidateUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val emailUseCase: ValidateEmailUseCase,
    private val phoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val passwordUseCase: ValidatePasswordUseCase,
    private val usernameUseCase: ValidateUsernameUseCase,
    private val signupUpUseCase: SignUserUpUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
    private val _authResult: MutableStateFlow<AuthResult> = MutableStateFlow(AuthResult.SignedOut)
    private val _isFormValid = MutableStateFlow(false)

    val signupUiState = _uiState.asStateFlow()
    val authState: StateFlow<AuthResult> = _authResult
    val isFormValid: StateFlow<Boolean> = _isFormValid
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

            AuthEvent.Submit -> {
                if (validateFields()) {
                    val newUser = with(_uiState.value) {
                        NewUserData(
                            username = username,
                            email = email,
                            password = password,
                            phoneNumber = phoneNumber
                        )
                    }
                    signUp(newUser)

                }

            }
        }
    }

    private fun validateFields(): Boolean {
        _isFormValid.value =
            (validateUsername() && validateEmail() && validatePassword() && validatePhoneNumber())

        return _isFormValid.value
    }

    private fun signUp(userInfo: NewUserData) {

        viewModelScope.launch{
            _authResult.value = signupUpUseCase(userInfo)
//            TODO: add loading indicator to button
            when (val result = _authResult.value) {
                is AuthResult.Error -> {
                    if (result.errorCode == ErrorCode.DUPLICATE_EMAIL) {
                        _uiState.value = _uiState.value.copy(
                            emailError = FieldError(
                                isError = true,
                                ValidationResult.DUPLICATE_EMAIL
                            )
                        )
                    }
                    if (result.errorCode == ErrorCode.DUPLICATE_PHONE_NUMBER) {
                        _uiState.value = _uiState.value.copy(
                            emailError = FieldError(
                                isError = true,
                                ValidationResult.DUPLICATE_PHONE_NUMBER
                            )
                        )

                    }
                }

                else -> {}
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

    private fun validatePhoneNumber(): Boolean {
        val result = phoneNumberUseCase(_uiState.value.phoneNumber)
        _uiState.value = _uiState.value.copy(
            phoneNumberError = result
        )
        return !result.isError
    }

    private fun validateUsername(): Boolean {
        val result = usernameUseCase(_uiState.value.username)
        _uiState.value = _uiState.value.copy(
            usernameError = result
        )
        return !result.isError
    }


}