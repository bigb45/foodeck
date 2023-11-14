package com.example.data.models

import com.google.firebase.database.DataSnapshot

data class UserSignUpModel(
    val email: String,
    val password: String,
    val username: String,
    val phoneNumber: String,
)

data class UserLoginCredentials(
    val email: String,
    val password: String,
)

data class UserData(
    val userId: String?,
    val username: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val profilePictureUrl: String? = null,
)
