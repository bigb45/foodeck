package com.example.authentication.email_login

import com.example.data.models.FieldError



data class SignInScreenState(
    var email: String = "",
    val password: String = "",

    val emailError: FieldError = FieldError(
        false, null
    ),

    val passwordError: FieldError = FieldError(
        false, null
    ),
)





