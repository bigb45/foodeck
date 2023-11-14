package com.example.authentication.create_account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.AuthResult
import com.example.common.Result
import com.example.common.asResult
import com.example.data.data.FieldError
import com.example.data.models.UserSignUpModel
import com.example.authentication.AuthEvent
import com.example.data.repositories.AuthRepositoryImpl
import com.example.data.util.ValidationResult
import com.example.domain.use_cases.SignUserUpUseCase
import com.example.domain.use_cases.ValidateEmailUseCase
import com.example.domain.use_cases.ValidatePasswordUseCase
import com.example.domain.use_cases.ValidatePhoneNumberUseCase
import com.example.domain.use_cases.ValidateUsernameUseCase
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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
    private val _uiState = MutableStateFlow(CreateAccountScreenState())
    private val _authResult: MutableStateFlow<AuthResult> = MutableStateFlow(AuthResult.SignedOut)
    private val _isFormValid = MutableStateFlow(false)

    val signupUiState = _uiState.asStateFlow()
    val authState: StateFlow<AuthResult> = _authResult
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
                    _authResult.value = AuthResult.Loading
                    val newUser = with(_uiState.value) {
                        UserSignUpModel(
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

    private fun signUp(user: UserSignUpModel) {
        viewModelScope.launch {

            signupUpUseCase(user).asResult().collect { result ->

                Log.d("error", "$result")
                when (result) {
                    is Result.Success -> {
                        _authResult.value = AuthResult.Success(result.data)
                    }

                    Result.Loading -> {
                        _authResult.value = AuthResult.Loading
                    }

                    is Result.Error -> {
                        handleAuthError(result)
                        _authResult.value = AuthResult.Error(result.exception?.message ?: "Unknown")

                    }
                }


            }
        }
//            .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = AuthResult.Loading,
//        )

    }

    private fun handleAuthError(result: Result.Error) {
        when (result.exception) {
            is FirebaseAuthUserCollisionException -> {
                _uiState.value = _uiState.value.copy(
                    emailError = FieldError(
                        isError = true,
                        ValidationResult.DUPLICATE_EMAIL
                    )
                )
            }

            is AuthRepositoryImpl.DuplicatePhoneNumberError -> {
                _uiState.value = _uiState.value.copy(
                    phoneNumberError = FieldError(
                        isError = true,
                        ValidationResult.DUPLICATE_PHONE_NUMBER
                    )
                )
            }

            else -> {}
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