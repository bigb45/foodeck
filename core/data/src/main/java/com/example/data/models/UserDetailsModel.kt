package com.example.data.models



data class UserDetailsModel(
    val userId: String?,
    val username: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val profilePictureUrl: String? = null,
)
