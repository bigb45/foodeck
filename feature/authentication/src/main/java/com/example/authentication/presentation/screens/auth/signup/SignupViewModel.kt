package com.example.authentication.presentation.screens.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.authentication.presentation.screens.auth.data.AuthEvent
import com.example.authentication.domain.use_cases.ValidateEmailUseCase
import com.example.authentication.domain.use_cases.ValidatePasswordUseCase
import com.example.authentication.domain.use_cases.ValidatePhoneNumberUseCase
import com.example.authentication.domain.use_cases.ValidateUsernameUseCase
import com.example.authentication.presentation.screens.auth.data.AuthResult
import com.example.authentication.presentation.screens.auth.data.AuthState
import com.example.authentication.presentation.screens.auth.data.UserData
import com.google.firebase.FirebaseError.ERROR_EMAIL_ALREADY_IN_USE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val emailUseCase: ValidateEmailUseCase,
    private val phoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val passwordUseCase: ValidatePasswordUseCase,
    private val usernameUseCase: ValidateUsernameUseCase,
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
                if(validateFields()){
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
        val auth = Firebase.auth
        _authResult.value = AuthResult.Loading
//        check if username available
//        false: _uistate.usernameError = "already in use"
//        true: continue
        auth.createUserWithEmailAndPassword(userInfo.email, userInfo.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authResult.value = AuthResult.Success(
                        data = UserData(
                            username = userInfo.username,
                            userId = task.result.user?.uid ?: "test_id",
                            profilePictureUrl = null
                        )
                    )
                } else {
                    when(task.exception){

                    }
                    _authResult.value = AuthResult.Error(task.exception?.message ?: "Unknown error")
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