package com.example.data.models

import com.google.gson.annotations.SerializedName

data class RestaurantSection(
    @SerializedName("section_id")
    val sectionId: String,
    @SerializedName("section_title")
    val sectionTitle: String,
    @SerializedName("StoreItem")
    val menuItems: List<MenuItem>,
)

