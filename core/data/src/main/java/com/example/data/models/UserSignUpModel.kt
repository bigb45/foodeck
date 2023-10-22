package com.example.data.models

data class UserSignUpModel(
    val email: String,
    val password: String,
    val username: String,
    val phoneNumber: String
)

data class UserLoginCredentials(
    val email: String,
    val password: String
)