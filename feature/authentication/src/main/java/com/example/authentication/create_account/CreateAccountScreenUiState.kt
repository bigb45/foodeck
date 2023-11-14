package com.example.authentication.create_account

import com.example.data.data.FieldError


sealed interface TextFieldState{
    data class Error(val label: String, val errorMessage: String): TextFieldState
    data class Success(val label: String, val data: String?): TextFieldState
}

data class CreateAccountScreenState(
    val username: String = "",
    var email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val usernameError: FieldError = FieldError(
        false, null
    ),
    val emailError: FieldError = FieldError(
        false, null
    ),
    val phoneNumberError: FieldError = FieldError(
        false, null
    ),
    val passwordError: FieldError = FieldError(
        false, null
    ),
)





