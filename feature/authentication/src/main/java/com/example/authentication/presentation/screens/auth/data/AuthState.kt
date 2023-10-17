package com.example.authentication.presentation.screens.auth.data

import com.example.authentication.util.ValidationResult


// not working

//sealed interface TextFieldState{
//    data class Error(val label: String, val errorMessage: ValidationResult?): TextFieldState
//    data class Success(val label: String, val data: String?): TextFieldState
//}
//
//data class SignUpState(
//    val usernameState: TextFieldState = TextFieldState.Success("Username", null),
//    var emailState: TextFieldState = TextFieldState.Success("Email", null),
//    val phoneNumberState: TextFieldState = TextFieldState.Success("Phone Number", null),
//    val passwordState: TextFieldState = TextFieldState.Success("Password", null),
//)
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
//sealed interface AuthStateTest {
//
//    data class EmailData(
//        val error: FieldError,
//        val data: String?,
//    ) : AuthStateTest
//
//    data class PhoneNumberData(
//        val error: FieldError,
//        val data: String?,
//    ) : AuthStateTest
//
//    data class PasswordData(
//        val error: FieldError,
//        val data: String,
//    ) : AuthStateTest
//
//}
data class FieldData(
    val isError: Boolean = false,
    val errorMessage: ValidationResult? = null,
    val data: String? = null,
)

sealed interface AuthResult {
    object Loading : AuthResult
    object Cancelled : AuthResult
    object SignedOut : AuthResult
    data class Error(
        val errorMessage: String,
    ) : AuthResult

    data class Success(
        val data: UserData
    ) : AuthResult

}

