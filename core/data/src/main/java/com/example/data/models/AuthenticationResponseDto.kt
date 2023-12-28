package com.example.data.models

data class AuthenticationResponseDto(
    val userId: String,
    val accessToken: String,
    val refreshToken: String
)

