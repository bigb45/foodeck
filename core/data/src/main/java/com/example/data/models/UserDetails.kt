package com.example.data.models



data class UserDetails(
    val userId: String?,
    val name: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val profilePictureUrl: String? = null,
)
