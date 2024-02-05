package com.example.data.models

import com.google.gson.annotations.SerializedName

data class BentoSection(
    @SerializedName("id") val id: String,
    @SerializedName("name") val title: String,
    @SerializedName("title") val subtitle: String,
    @SerializedName("image_url") val imageUrl: String?,

)
