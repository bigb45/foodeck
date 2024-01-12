package com.example.data.models

import com.google.gson.annotations.SerializedName

data class UserDto(
    val userId: String,
    val name: String,
    val email: String,
)