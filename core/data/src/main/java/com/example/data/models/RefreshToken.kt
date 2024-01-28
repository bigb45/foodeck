package com.example.data.models

import com.google.gson.annotations.SerializedName

data class RefreshToken(
    @SerializedName("accessToken") val token: String
)
