package com.example.data.models

import com.google.gson.annotations.SerializedName

data class TokenDto(
    val userId: String,
    val accessToken: String,
    val refreshToken: String
)

