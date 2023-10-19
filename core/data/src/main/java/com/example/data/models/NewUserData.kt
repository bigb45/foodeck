package com.example.data.models

data class NewUserData(
    val email: String,
    val password: String,
    val username: String,
    val phoneNumber: String
)

data class UserInfo(
    val email: String,
    val password: String
)