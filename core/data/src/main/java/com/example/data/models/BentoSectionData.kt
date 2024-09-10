package com.example.data.models

import com.google.gson.annotations.SerializedName

data class BentoSectionData(
    @SerializedName("id")
    val sectionId: String,
    @SerializedName("name")
    val sectionTitle: String,
    @SerializedName("title")
    val sectionSubtitle: String,
    @SerializedName("image_url")
    val imageUrl: String // TODO: make it URL
)
