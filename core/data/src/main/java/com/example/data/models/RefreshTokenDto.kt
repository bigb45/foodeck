package com.example.data.models

import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
    @SerializedName("accessToken") val token: String
)
