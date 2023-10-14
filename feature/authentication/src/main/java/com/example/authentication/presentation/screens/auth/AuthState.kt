package com.example.authentication.presentation.screens.auth

import com.example.authentication.util.FieldError

// TODO: convert data class to sealed interface, handle different login states (success, failure, unauthorized)
data class AuthState(
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


// TODO: convert data class to sealed interface, handle different login states (success, failure, unauthorized)
sealed interface AuthStateTest {

    data class UserInfo(
        val username: String = "",
        var email: String = "",
        val phoneNumber: String = "",
        val password: String = "",
    ) : AuthStateTest

    data class Error(
        val usernameError: FieldError,
    )

    data class UsernameError(
        val data: FieldError,
    ) : AuthStateTest

    data class EmailError(
        val data: FieldError,
    ) : AuthStateTest

    data class PhoneNumberError(
        val data: FieldError,
    ) : AuthStateTest

    data class PasswordError(
        val data: FieldError,
    ) : AuthStateTest
}

sealed interface AuthResult {
    object Loading : AuthResult
    object Cancelled : AuthResult
    data class Error(
        val errorMessage: String,
    ) : AuthResult

    data class Success(val data: UserData) : AuthResult

}

