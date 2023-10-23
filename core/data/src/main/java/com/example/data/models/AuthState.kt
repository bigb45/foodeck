package com.example.data.models

import com.example.data.data.FieldError
import com.example.data.data.UserData


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
//

//data class FieldData(
//    val isError: Boolean = false,
//    val errorMessage: ValidationResult? = null,
//    val data: String? = null,
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



sealed interface AuthResult {
    object Loading : AuthResult
    object Cancelled : AuthResult
    object SignedOut : AuthResult
    data class Error(
        val errorMessage: String,
        val errorCode: ErrorCode = ErrorCode.OTHER
    ) : AuthResult

    data class Success(
        val data: UserData
    ) : AuthResult

}

enum class ErrorCode{
    DUPLICATE_EMAIL,
    DUPLICATE_PHONE_NUMBER,
    INCORRECT_PASSWORD,
    OTHER,
    UNREGISTERED_USER,
}
